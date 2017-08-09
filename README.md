# lorempixel-downloader
A simple script that downloads a bunch of random ~~images~~ kittens from http://lorempixel.com/ to a folder.

![Kittens](https://github.com/santafebound/lorempixel-downloader/blob/master/cats.jpg)

## I Can Haz 400 Kittens!?

Yes, you can haz! Just open this script in your favorite IDE (Eclipse, IntelliJ, etc.), configure the properties file to your liking, and download away! Just make sure you read the license (https://github.com/santafebound/lorempixel-downloader/blob/master/LICENSE) before abusing the shit out of this script because the guys at FelixHoller.com might get irritated.

## config.properties

Open the config.properties file in a text editor and modify the following values to customize your script execution. You can download more than just cats! Choose where you want to save your images, and what size you want them to be. Cheers!

```java
downloadPath=C:\\Temp\\lorempixel\\images
width=800
height=600
numImages=5
category=cats

## "category" can be one of {abstract, city, people, transport, animals, food, nature, business, nightlife, sports, cats, fashion, technics}
## if "category" is empty, the script will download images from random categories
```
