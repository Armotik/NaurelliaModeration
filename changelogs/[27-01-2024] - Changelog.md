# NaurelliaModeration - Changelog

## Moderation Plugin for NaurelliaCraft
### Author : Armotik
### API Version 1.20.4

---

```
## [1.20.4] - 2024-01-27
### Added
- Add a new command : /connections <player> (get all the accounts of the player)
- Add a new command : /vanish 
- Add a new method in the FilesReader class : writeConnections()
- Add Javadoc to the ConnectionsManager class
- Add Javadoc to the FilesReader class
- Add new enum class : ResultCodeType
- Add option to the Main Menu Gui -> Vanish
- Add option to the Main Menu Gui -> Teleport to the target (if online)
- Add option to the Main Menu Gui -> Connections count (if online or in the database)

### Changed
- Change the way the plugin check for the connection of the player in the ConnectionManager class
- Change the unban command to handle deprecated methods
- Change the kick method in the SanctionManager class to handle null staff value
- Modify the Mod Menu Gui to not be able to open a player inventory if the player is not online
- Modify the Mod Menu Gui to change the color of the vanish option if the player is vanished (red if vanished, green if not)
- Modify the Reports Gui, a staff member is now not able to resolve a player if the report is about him
- Modify the Reports Gui, if a staff member resolve a report, send a message to the player who reported the player if he is online (except for admin)

### Removed
```