# GeikPlugins-Discord-Socket

Discord bot socket for LeaderOS. Websender integration and License Managment

- PHP to Java socket listening
- MYSQL License check (On Join)
- PHP Purchase packet sending to Java with socket.
- Command usage as purchase

Console Applicattion can be use with Windows Batch File & Linux Shell Script.

Windows Batch File Example:

```
java -Xmx512M -jar GeikPlugins-1.0.0-SNAPSHOT.jar
PAUSE 
```

Linux Shell Script Example:

```
#!/bin/sh
echo screen -r BOT - for open screen of bot's screen
screen -d -m -S "BOT" java -Xmx512M -jar GeikPlugins-1.0.0-SNAPSHOT.jar
```

This bot has automatic JDA & MySQL Connection.
