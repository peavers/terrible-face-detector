# terrible-face-detector
Find faces in a file and return their coordinates 

## Running

The `terrible-face-detector` requires the OpenCV native bindings to be on the library path of the JVM. 
This can be achieved using as the JVM Arguments. These bindings are built for Ubuntu 20.04 so YMMV 
```
-Djava.library.path=$PROJECT_DIR$/lib/native
```  
