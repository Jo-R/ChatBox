package com.jorigg.android.chatbox.model;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XmlWriteRead {

    //is called when pausing config activity - does it need calling anywhere else?
    public static void writeChatsToXML(ArrayList<Conversation> chatLibrary, Context context) {

        try {
            FileOutputStream fileos = context.openFileOutput("chatData.xml", Context.MODE_PRIVATE);

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

                xmlSerializer.startTag(null, "Type");
                xmlSerializer.text(convo.getClass().getSimpleName());
                xmlSerializer.endTag(null, "Type");

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
            xmlSerializer.startTag(null, "Dialogue");
            while (e.hasNext()) {
                Map.Entry pair = e.next();

                xmlSerializer.startTag(null, "Element");
                xmlSerializer.text(pair.getKey().toString());
                xmlSerializer.endTag(null, "Element");

                ArrayList<Sentence> sentences = (ArrayList<Sentence>) pair.getValue();
                xmlSerializer.startTag(null, "Sentences");
                for (Sentence sentence : sentences) {
                    xmlSerializer.startTag(null, "Content");
                    xmlSerializer.text(sentence.getContent());
                    xmlSerializer.endTag(null, "Content");
                }
                xmlSerializer.endTag(null, "Sentences");
            }
            xmlSerializer.endTag(null, "Dialogue");
        }  catch (IOException e5) {
            // TODO Auto-generated catch block
            e5.printStackTrace();
        }
    }

    public static void parseChatsFromXml(Context context) {
        try {

            ChatBank cb = ChatBank.get(context);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            File dataFile = new File(context.getFilesDir(), "chatData.xml");
            xpp.setInput(new FileReader(dataFile));

            int eventType = xpp.getEventType();
            //declare the type and title here so only get reset when a new conversation tag
            // is encountered
            String type = "";
            Conversation convo = null;
            ConversationElementEnum element = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                //for all start tags get the name then do somehting with it
                if (eventType == XmlPullParser.START_TAG) {

                    String tagName = xpp.getName();

                    if (tagName.equals("Conversation")) {
                        xpp.next(); //gets the type tag
                        tagName = xpp.getName();
                        if (tagName.equals("Type")) {
                            xpp.next(); //gets the type text
                            type = xpp.getText();

                            xpp.next(); //type end tag
                            xpp.next(); //title open tag
                            xpp.next(); //title text
                            String title = xpp.getText();

                            if (type.equals("AskForSomething")) {
                                cb.addNewConversation(title, "Ask for Something");
                                convo = cb.getConversation(title);
                            }
                        }
                    }


                    if (tagName.equals("Element")) {
                        xpp.next();
                        String elementStr = xpp.getText();
                        if (type.equals("AskForSomething")) {
                            element = AskForSomething.AskForSomethingElements.valueOf
                                    (elementStr);
                        }

                    }


                    if (tagName.equals("Sentences")) {
                        xpp.next(); //content tag
                        tagName = xpp.getName();
                        while (tagName.equals("Content")) {
                            xpp.next(); //content itself
                            String content = xpp.getText();
                            convo.addToConversation(element, content);
                            xpp.next();
                            xpp.next();
                            tagName = xpp.getName();
                        }
                    }



                }

                eventType = xpp.next();

            }


        } catch (XmlPullParserException e) {
            //TODO handle it
            e.printStackTrace();
        } catch (FileNotFoundException e2) {
            //TODO handle it
        } catch (IOException e) {
            //TODO handle it
            e.printStackTrace();
        }

    }


}
