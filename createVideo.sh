#!/bin/bash

audioFile="./Creations/.AudioFiles/$1.wav"
visualOnlyVideo="./Creations/${1}.mp4"
finalCreation="./Creations/${1}.avi"

length=`soxi -D ${audioFile}`

#Makes the visual part of the video
ffmpeg -y -f lavfi -i color=c=blue:s=320x240:d=${length} -vf "drawtext=fontsize=30:fontcolor=white:x=(w-text_w)/2:y=(h-text_h)/2:text='${2}'" "${visualOnlyVideo}" &> /dev/null

#Combines the video and audio into a new video	
ffmpeg -y -i "${visualOnlyVideo}" -i "${audioFile}" -map 0:v -map 1:a "${finalCreation}" &> /dev/null

#Removes the temporary visual only video
rm "${visualOnlyVideo}"
    