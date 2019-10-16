# thug-pro-hotkeys

Hotkeys for typed commands in THUG Pro - created by choko

## Download

You can download the EXE from the [releases](https://www.github.com/byxor/thug-pro-hotkeys/releases) page.

## Usage

1. Launch THUG Pro
2. Launch thug-pro-hotkeys.exe

```
F5 - Set Restart Point.
F6 - Goto Restart Point.
F7 - Observe Players.
F8 - Warp to Player.
```

## Compilation (For Developers)

You can compile the program using [Mono](http://www.mono-project.com/docs/getting-started/install/windows/).

If you have bash, run:

```
./compile.sh <optionalExecutableName>
```

Otherwise, run:

```
mcs -reference:System.Windows.Forms -out:thug-pro-hotkeys.exe *.cs
```
