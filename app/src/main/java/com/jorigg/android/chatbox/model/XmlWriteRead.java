package com.jorigg.android.chatbox.model;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XmlWriteRead {

    //todo will need to call this from an activity class - which is probably fine - do it on
    // destroying config screen since that is where any changes will be??
    public static void writeChatToXML(ArrayList<Conversation> chatLibrary, Context context) {

        try {
            //TODO THIS MIGH NOT BE RIGHT - do I want to overwrite whole file each time?? Probably??
            FileOutputStream fos = new FileOutputStream("chatData.xml");
            FileOutputStream fileos = context.openFileOutput("chatData", Context.MODE_PRIVATE);

            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);

            //START DOCUMENT
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "ChatLibrary");

           //DATA

            for (Conversation convo : chatLibrary) {
                HashMap<ConversationElementEnum, ArrayList<Sentence>> dialogue =
                     convo.getDialogue();
                xmlSerializer.startTag(null, "Conversation");
                xmlSerializer.startTag(null, "Title");
                xmlSerializer.text(convo.getTitle());
                xmlSerializer.endTag(null, "Title");
                writeDialogue(dialogue, xmlSerializer);
                xmlSerializer.endTag(null, "Conversation");
            }


            //END DOCUMENT
            xmlSerializer.endTag(null, "ChatLibrary");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        catch (IllegalStateException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }
        catch (IOException e4) {
            // TODO Auto-generated catch block
            e4.printStackTrace();
        }
    }

    private static void writeDialogue(HashMap<ConversationElementEnum, ArrayList<Sentence>> convo,
                                      XmlSerializer xmlSerializer) {
        //iterate of enummap and get each element and sentence
        Iterator<Map.Entry<ConversationElementEnum, ArrayList<Sentence>>> e = convo.entrySet().iterator();
        try {
            while (e.hasNext()) {
                Map.Entry pair = e.next();

                xmlSerializer.startTag(null, "Dialogue");

                xmlSerializer.startTag(null, "Element");
                xmlSerializer.text(pair.getKey().toString());
                xmlSerializer.endTag(null, "Element");

                ArrayList<Sentence> sentences = (ArrayList<Sentence>) pair.getValue();
                xmlSerializer.startTag(null, "Sentences");
                for (Sentence sentence : sentences) {
                    xmlSerializer.startTag(null, "Content");
                    xmlSerializer.text(sentence.getContent());
                    xmlSerializer.endTag(null, "Content");
                    xmlSerializer.startTag(null,"SpeechType");
                    xmlSerializer.text(sentence.getSpeechType().toString());
                    xmlSerializer.endTag(null, "SpeechType");
                }
                xmlSerializer.endTag(null, "Sentences");

                xmlSerializer.endTag(null, "Dialogue");

            }
        }  catch (IOException e5) {
            // TODO Auto-generated catch block
            e5.printStackTrace();
        }
    }


}
