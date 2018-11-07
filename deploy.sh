#!/bin/bash



deploy(){
    scp -P 5422 *.zip kvalleydb@sftp.kollectvalley.my:/home/kvalleydb/notifier
    
    
}


deploy
