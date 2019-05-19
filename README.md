# ButtonGroup

## Installation

## Usage
XML Code
```xml
<com.hunteralex.buttongroup.ButtonGroup
        android:id="@+id/buttonGroup1"
        android:layout_width="300dp"
        android:layout_height="wrap_content"
        app:buttonHeight="50dp"/>
```
JAVA code
```Java
ArrayList<String> arrayList = new ArrayList<>();
arrayList.add("First");
arrayList.add("Middle");
arrayList.add("Last");

ButtonGroup buttonGroup = findViewById(R.id.buttonGroup1);
buttonGroup.addButtons(arrayList);
```
