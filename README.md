# dataknowl-java-sdk

DataKnowl Java SDK

# Account

Create a DataKnowl Cloud Service account [here.](https://cloud.dataknowl.com)

With V-Agent Free Tier you can use V-Agent for free for 12 months, so you have a full year to develop and test, without spending anything. 

# Quickstart

### Call V-Agent API using Java SDK
```java
package com.dataknowl.dcs.demo.vagent;

import com.dataknowl.dcs.sdk.client.Auth;
import com.dataknowl.dcs.sdk.client.Region;
import com.dataknowl.dcs.sdk.vagent.DcsVAgentClient;
import com.dataknowl.dcs.sdk.vagent.VAgentClient;
import com.dataknowl.dcs.sdk.vagent.model.ConvSequenceInput;
import com.dataknowl.dcs.sdk.vagent.model.ConvSequenceReply;
import com.dataknowl.dcs.sdk.vagent.model.ConvSequenceResult;
import com.dataknowl.dcs.sdk.vagent.model.Rid;
import java.util.List;

/**
 *
 * @author Complexity Intelligence, LLC
 */
public class VAgentProcess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String accessKeyId =  "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        String secretAccessKey = "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY";
        
        String vagentRid = "VAGNTRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR";
        String channelId = "api";
        
        VAgentClient vagent = DcsVAgentClient.build(vagentRid, channelId,
                new Auth(accessKeyId, secretAccessKey),
                Region.EU_CENTRAL);
        
        String inputText = "How can I create an account ?";
        
        ConvSequenceInput sequence = new ConvSequenceInput
                .ConvSequenceInputBuilder(new Rid("NEW"), inputText).build();
        
        ConvSequenceResult reply = vagent.processConvSequence(sequence);
        
        ConvSequenceReply replySequence = reply.getReplySequence();
        
        List<String> textList = replySequence.getTextList();
        
        for(String text : textList) {
            System.out.println("Conversation RID: " + replySequence.getRid().get());
            System.out.println("Reply: " + text);
        }
        
    }
    
}
```

# Documentation

The documentation for the DataKnowl Cloud API can be found [here.](http://kb.cloud.dataknowl.com/en/homepage)

# Getting help

If you need help installing or using the library, please contact DataKnowl Support at support@dataknowl.com
