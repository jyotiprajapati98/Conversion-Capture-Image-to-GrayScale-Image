# Conversion-Capture-Image-to-GrayScale-Image


This is small and my first android application where am capturing image using Android Camera then convert that image into Gray Scale image using OpenCV.
Download the project and run.

For this whole process i using -


for developing your own Project follow this steps -

This is small and my first android application where am capturing image using Android Camera then convert that image into Gray Scale image using OpenCV.
For this whole process i using -
1.Android Studio 4.0.0
2.OpenCV-3.4.3

Download Android Studio https://android-studio.en.uptodown.com/windows/versions
Download OpenCV - https://opencv.org/releases/

After that just setup the android studio and create a new project .
In step two for OpenCV -

1)Download Opencv Android SDK from above link .
2)Extract the .zip file.

Open your android studio project -

File->New->import module
when the popup open just go into your SDK folder path and follow -
Downloads-> OpenCV-3.4.3-android-sdk -> OpenCV-android-sdk -> sdk - >java

then next then finish .
For next do one more setup 
Copy the libs file - C:\Users\HP\Downloads\OpenCV-3.1.0-android-sdk\OpenCV-android-sdk\sdk\native\libs
make a new folder in android studio - right click on app ->new ->folder->JNI then create the folder name - jniLibs.

then Do Gradle Sync your project.


