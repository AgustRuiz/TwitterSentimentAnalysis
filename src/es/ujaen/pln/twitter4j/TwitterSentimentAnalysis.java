/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.pln.twitter4j;

import java.util.logging.Level;
import twitter4j.*;

/**
 *
 * @author AgustRuiz <arl00029@red.ujaen.es>
 */
public class TwitterSentimentAnalysis {

    private Twitter twitter;// = new TwitterFactory().getInstance();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("HOLA, ESTOY EMPEZANDO");
        test();
        System.out.println("ADIOS, YA HE TERMINADO");
    }

    public static void test() {
        Twitter twitter = TwitterFactory.getSingleton(); //Hay que podar la "broza" que saca
        Query query = new Query("@AgustRuiz");

        QueryResult result;
        try {
            result = twitter.search(query);
            System.out.println("\n\n\nTWEETS:");
            for (Status status : result.getTweets()) {
                System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
            }
        } catch (TwitterException e) {
            System.out.println("Error: "+e.toString());
        }

    }

}
