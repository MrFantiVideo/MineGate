# <p><img src="https://libraries.minegate.fr/assets/logo_ico.png" width="65px" height="65px" align="center" alt="MineGate"> MineGate · MoreBlocks (Minecraft)</img></p>

Ce mod (Fabric) ajoute de nombreux blocs et de nouvelles variantes.

### 🏡 Installation :

```> gradlew genSources```

```> gradlew vscode``` <i>(Uniquement sous Visual Code Studio)</i>

Une extension est requise pour IntelliJ IDEA pour ne pas avoir d'erreur avec les mixins.<br>
Minecraft Development : https://plugins.jetbrains.com/plugin/8327-minecraft-development/

### 🌲 Compilation :

```> gradlew build```
 
### 🔨 Mises à jour :

Vous devez d'abord changer les versions des fichiers suivants :
- gradle.properties
- fabric.mod.json

Ensuite supprimer tous les fichiers et dossiers avec un « . » devant à l'exception de ceux commençant par « .git » qui sont utilisés par Github.

### ⚙ Intégration :

Si vous souhaitez l'intégrer dans votre mod, il vous suffit d'ajouter ceci dans le « build.gradle » :

```
repositories
{
     mavenCentral()
     maven
     {
          name = "MineGate"
          url "https://libraries.minegate.fr/maven/"
     }
}

dependencies
{
     modImplementation "net.minegate.fr:minegate-moreblocks:${project.minegate_moreblocks_version}"
}
```

ainsi que ceci dans le « gradle.properties » :

	minegate_moreblocks_version=1.2.0

Vous devrez modifier ce fichier lorsqu'une nouvelle version du mod est publiée.<br>
Les anciennes versions resteront disponibles, mais pas recommandées à l'utilisation.

### 📚 Liens utiles :

Fabric Client & Serveur : https://fabricmc.net/use/ <br>
Fabric API : https://www.curseforge.com/minecraft/mc-mods/fabric-api <br>
Documentation : https://fabricmc.net/wiki/tutorial:introduction <br>
Dernières versions : https://modmuss50.me/fabric.html

### 🚇 Discord :

[![discord](https://discordapp.com/api/guilds/688858198488252467/embed.png?style=banner2)][discord]

MineGate<br>
Tous droits réservés.

[discord]: https://discord.gg/uYEWHPw "MineGate (Discord)"
