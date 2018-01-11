## react-native-manager

This is a react native module that handle all of the phone's function.


## Installation

```js
npm install react-native-manager --save
```

## Usage Example

```js
import Manager from 'react-native-manager';

async fun() {
    try{
        console.log(await Manager.getSms({count:10}));
    }catch(e){
        console.log(e);
    }

}
```

## Options

There are lots of options to filter the returned SMS:

```javascript

var filter = {
    _id: "",                    // autoincrement, start from 1
    thread_id: "",              // thread_id，the same sender's ID is the same
    address: "",                // number of the sender
    person: "",                 // Contacts，null for stranger
    date: "",                   // date
    protocol: "",               // 0 SMS_RPOTO, 1 MMS_PROTO
    read: "",                   // 0 unread， 1 read
    status: "",                 // -1 received，0 complete, 64 pending, 128 failed
    type: "",                   // 1 receive，2 send
    body: "",                   // message content
    service_center: "",         // SMS service center number
    subject: "",                // subject
    reply_path_present: "",     // TP-Reply-Path
    locked: "",                 // locked
    sms_type: "",               // '' for all (default), 'sent', 'draft', 'outbox', 'failed', 'queued'
    count: 10                   // count of SMS to return each time
};

async fun() {
    try{
        console.log(await Manager.getSms(filter));
    }catch(e){
        console.log(e);
    }

}
```

## Getting Started - Android
* In `android/setting.gradle`
```gradle
...
include ':react-native-manager'
project(':react-native-manager').projectDir = new File(settingsDir, '../node_modules/react-native-manager/android')
```

* In `android/app/build.gradle`
```gradle
...
dependencies {
    ...
    compile project(':react-native-manager')
}
```

If using RN 0.29+, register module (in android/app/src/main/java/[your-app-namespace]/MainApplication.java)
``` java
// ...
import com.reactnativemanager.manager.*;    // <--

public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost reactNativeHost = new ReactNativeHost(this) {

            // ...

            @Override
            protected List<ReactPackage> getPackages() {
                return Arrays.<ReactPackage>asList(
                    
                    new ManagerPackage(),   // <--
                    
                    // ...
                );
            }
        };
    }

    // ...
}
```

* add Sms permission (in android/app/src/main/AndroidManifest.xml)
```xml
...
  <uses-permission android:name="android.permission.READ_SMS" />
...
```
