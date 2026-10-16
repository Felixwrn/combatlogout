# CombatLogout

Minecraft **26.3** Plugin für Paper mit **Java 25**. Die Paper-API ist auf `26.3.build.200-stable` festgelegt.

## Bauen

```sh
bash ./gradlew --no-daemon build
```

Die Plugin-JAR liegt in `build/libs/`. Kopiere sie in den `plugins/`-Ordner eines Paper-26.3-Servers und starte den Server mit Java 25.

Für einen lokalen Entwicklungsserver ist `bash ./gradlew runServer` auf Minecraft 26.3 konfiguriert. Der Server erstellt seine Dateien im ignorierten Verzeichnis `run/`; beachte dabei die Minecraft-Server-EULA.
