# `Object Diff.`

**`Type Notation`**

DateType :
This can use to format Date format. 

EntityType : 
This is to configure property as Entity Type. should provide name. 

FieldAlias : This is to represent alias for property. if not lib will create camel case property name. 

IgnoreDiff : Can ignore diff. 

ObjectDiffType : Can provide custom ObjectDiffPrinter.

ParentDiff :  can provide no of parent to consider or ignore parent diff. 

ValueEvaluator : default value evaluate through bject equal method. can provide custom ValueEvaluator. 

ValueOutput : Can provide custom ValueOutputHandler. 


**`Process`**

To generate DIFF :
 
`DiffBuilder builder = new DiffBuilder();`

`builder.diff(user1, user2);`

Through builder you can get String diff or DiffObject which contain fields. 
Also you can add custom type through builder which effect to whole diff process. 



 
