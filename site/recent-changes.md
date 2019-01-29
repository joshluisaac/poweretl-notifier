# Recent changes to PowerETL Notifier.

This document tracks recent changes to PowerETL Notifier.

## Release 1.1

###### Changes:
* Improved README documentation that describes how to build,configure, start and stop PowerETL Notifier.
* Added documentation to list the recent changes to PowerETL Notifier.
* Deleted application2.properties which is a redundant file.
* Added default procid file in logs directory. 
* Added `app.attachment.maxlimit` property in [application.properties](src/main/resources/application.properties) file which restricts files greater than the specified limit.
* Added bold red font for rejected records so that operators attention is drawn to it.
* Added more metrics such as loaded and rejected percentage which is a relative measure against the overall records.
* [2019-01-29] Created an API endpoint that allows sending of DC email notification on ad-hoc/urgent basis. This comes handy in non production environments such as SIT and UAT when you need to notify after loading is completed.