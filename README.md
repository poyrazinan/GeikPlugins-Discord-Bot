# LeaderOS G-EDITION [Discord-Socket]

Web Socket and License Management for Discord Bot.

- PHP to Java socket listening
- MYSQL License check (On Join)
- PHP Purchase packet sending to Java with socket.
- Command usage as purchase

## How to Create Discord Bot?
Visit https://discord.com/developers/applications and create your application, then go to the BOT page and design what you want (Photo, name etc.)
Then close PUBLIC BOT settings and COPY BOT TOKEN to Main.java

## How to Run Discord Bot?

Console Applicattion can be use with Windows Batch File & Linux Shell Script.

Windows Batch File Example:

```
java -Xmx512M -jar LeaderOSGEdition-1.0-SNAPSHOT.jar
PAUSE 
```

Linux Shell Script Example:

```
#!/bin/sh
echo screen -r BOT - for open screen of bot's screen
screen -d -m -S "BOT" java -Xmx512M -jar LeaderOSGEdition-1.0-SNAPSHOT.jar
```

This bot has automatic JDA & MySQL Connection.