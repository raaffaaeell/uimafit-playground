# uimafit playground
Project containing some simple examples of uimaFIT annotators, consumers and document readers

This project requires maven 

`MainPipeline` is just an example of usage. You should configure it accordingly (inputs and outputs paths)

The `PearHelper` installs the PEAR archive and loads all it's dependencies of the folder lib in the classloader.

In the folder desc I have a PEAR package that can be used in the pipeline. It's the cas2mysql pear but with a sqlite descriptor. It should work without overriding configurations but if you want to do it there's no problem. Just beware that PEAR support in uimaFIT does not really exist and I created a custom helper to deal with it. 

In this case parameters `table` and `driver` should be untouched since the PEAR contains only the sqlite connector jar.


`HtmlWriter` will generate html files for each document and it's resources (js and css) in the same folder, jquery and bootstrap are the only dependencies for the html views.





