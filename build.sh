#!/usr/bin/env bash
set -Eeuo pipefail

ROOT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT_DIR"

# This project pins the Gradle daemon to Java 21 in
# gradle/gradle-daemon-jvm.properties. Prefer that JDK even when the shell's
# default `java` is an older version.
find_java_home() {
    local candidate

    if [[ -n "${JAVA_HOME:-}" && -x "$JAVA_HOME/bin/java" && \
        "$("$JAVA_HOME/bin/java" -version 2>&1)" == *'version "21.'* ]]; then
        candidate="$JAVA_HOME"
    else
        for candidate in \
            /usr/lib/jvm/java-21-openjdk \
            /usr/lib/jvm/jdk-21 \
            /opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home \
            /Library/Java/JavaVirtualMachines/*/Contents/Home; do
            if [[ -x "$candidate/bin/java" ]]; then
                printf '%s\n' "$candidate"
                return 0
            fi
        done

        if command -v java >/dev/null 2>&1 &&
            "$(java -version 2>&1)" == *'version "21.'*; then
            candidate="$(dirname -- "$(dirname -- "$(readlink -f "$(command -v java)")")")"
        fi
    fi

    if [[ -n "${candidate:-}" && -x "$candidate/bin/java" ]]; then
        printf '%s\n' "$candidate"
        return 0
    fi

    return 1
}

JAVA_HOME="$(find_java_home || true)"
if [[ -z "$JAVA_HOME" ]]; then
    echo "error: JDK 21 is required, but no usable Java installation was found." >&2
    echo "Install JDK 21 or set JAVA_HOME to a JDK 21 directory." >&2
    exit 1
fi

export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"

JAVA_MAJOR="$("$JAVA_HOME/bin/java" -version 2>&1 | sed -n 's/.*version "\([0-9][0-9]*\).*/\1/p' | head -n 1)"
if [[ "$JAVA_MAJOR" != "21" ]]; then
    echo "error: this project requires JDK 21; selected $JAVA_HOME (Java $JAVA_MAJOR)." >&2
    exit 1
fi

if [[ ! -x "$ROOT_DIR/gradlew" ]]; then
    echo "error: Gradle wrapper not found or not executable: $ROOT_DIR/gradlew" >&2
    exit 1
fi

echo "Using JDK $JAVA_MAJOR: $JAVA_HOME"
echo "Building debug APK..."
"$ROOT_DIR/gradlew" :app:assembleDebug "$@"

APK="$ROOT_DIR/app/build/outputs/apk/debug/app-debug.apk"
if [[ ! -f "$APK" ]]; then
    echo "error: build completed but APK was not found: $APK" >&2
    exit 1
fi

if ! command -v adb >/dev/null 2>&1; then
    echo "error: adb is required to install the APK. Add Android SDK platform-tools to PATH." >&2
    exit 1
fi

mapfile -t DEVICES < <(adb devices | awk '$2 == "device" { print $1 }')
if [[ -n "${ADB_SERIAL:-}" ]]; then
    if ! printf '%s\n' "${DEVICES[@]}" | grep -Fxq "$ADB_SERIAL"; then
        echo "error: ADB_SERIAL '$ADB_SERIAL' is not connected." >&2
        exit 1
    fi
elif [[ "${#DEVICES[@]}" -eq 1 ]]; then
    ADB_SERIAL="${DEVICES[0]}"
elif [[ "${#DEVICES[@]}" -eq 0 ]]; then
    echo "error: no Android phone is connected or USB debugging is disabled." >&2
    exit 1
else
    echo "error: multiple Android devices are connected; set ADB_SERIAL to choose one." >&2
    printf 'Connected devices: %s\n' "${DEVICES[*]}" >&2
    exit 1
fi

echo "Installing APK on $ADB_SERIAL..."
adb -s "$ADB_SERIAL" install -r "$APK"
echo "Launching com.tails1154.tailstalk2..."
adb -s "$ADB_SERIAL" shell monkey -p com.tails1154.tailstalk2 1 >/dev/null
echo "Installed and launched: $APK"
