# Dataknowl-Cloud-Java-SDK

DataKnowl Cloud Java SDK

# Account

Create a DataKnowl Cloud Service account [here.](https://cloud.dataknowl.com)

With V-Agent Free Tier you can use V-Agent for free for 12 months, so you have a full year to develop and test, without spending anything. 

# Installation

You can download the jar file [here](https://cdn.dataknowl.cloud/sdk/java/0.7/dataknowl-cloud-java-0.7.jar) .

The following listing shows all the libraries that need to be present in an application to use the SDK:
```
    <dependencies>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.2</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.21</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.6.2</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>2.6.2</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-1.2-api</artifactId>
            <version>2.6.2</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-slf4j-impl</artifactId>
            <version>2.6.2</version>
        </dependency>        
        <dependency>
            <groupId>org.jdom</groupId>
            <artifactId>jdom2</artifactId>
            <version>2.0.6</version>
        </dependency>
    </dependencies>
```



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

### Send SMS using Java SDK
```java
package com.dataknowl.dcs.demo.sms;

import com.dataknowl.dcs.sdk.client.Auth;
import com.dataknowl.dcs.sdk.client.Region;
import com.dataknowl.dcs.sdk.sms.DcsSmsClient;
import com.dataknowl.dcs.sdk.sms.SmsClient;
import com.dataknowl.dcs.sdk.sms.model.SmsSendResult;

/**
 *
 * @author Complexity Intelligence, LLC
 */
public class SmsSend {

    public static void main(String[] args) {
        
        String accessKeyId = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        String secretAccessKey = "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY";

        String fromId = "SMSIPxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        String to = "+1234567890";
        String body = "Hi Mark, your current balance is $578";
        
        SmsClient smsClient = DcsSmsClient.build(
                new Auth(accessKeyId, secretAccessKey), Region.EU_CENTRAL);
        
        SmsSendResult result = smsClient.send(fromId, to, body);

        System.out.println("Result code = " + result.getStatus());
        System.out.println("Result msg = " + result.getMessage());
    }
    
}
```

# Documentation

The documentation for the DataKnowl Cloud API can be found [here.](http://kb.cloud.dataknowl.com/en/homepage)

# Getting help

If you need help installing or using the library, please contact DataKnowl Support at support@dataknowl.com
