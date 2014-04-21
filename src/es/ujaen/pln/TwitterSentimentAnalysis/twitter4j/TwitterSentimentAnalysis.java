/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.pln.TwitterSentimentAnalysis.twitter4j;

import es.ujaen.pln.TwitterSentimentAnalysis.modelos.TweetPLN;
import java.util.List;
import java.util.logging.Level;
import twitter4j.*;
//import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author AgustRuiz <arl00029@red.ujaen.es>
 */
public class TwitterSentimentAnalysis {

    private static Twitter twitter;// = new TwitterFactory().getInstance();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String keywords[] = {"#GalaSV6"};
        twitterListener(keywords);
//        TwitterSentimentAnalysis.buscarTweets("GalaSV6", twitter);
    }

    public static void twitterListener(String[] keywords) {

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

        StatusListener listener = new StatusListener() {

            private Integer contador = 0;

            @Override
            public void onStatus(Status status) {
                ++contador;

                TweetPLN tweet = new TweetPLN(status);
                System.out.println("\n(" + contador + ") -----------------------------------------\n");
                System.out.println(tweet.getTweetId());
                System.out.println("@" + tweet.getUser());
                System.out.println("\n" + tweet.getTextoTweet());
                System.out.println("\n" + tweet.getTextoTweetProcesado());

//                System.out.println("----------------------------------------------\n(" + contador.toString() + " - "+status.getCreatedAt() + ") @" + status.getUser().getScreenName() + " - " + status.getText());
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice sdn) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onTrackLimitationNotice(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onScrubGeo(long l, long l1) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onStallWarning(StallWarning sw) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onException(Exception excptn) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        FilterQuery fq = new FilterQuery();

        fq.track(keywords);

        twitterStream.addListener(listener);
        twitterStream.filter(fq);
    }

    public static void buscarTweets(String busqueda, Twitter twitter) {

//         Configuración del registro de la aplicación: Se puede hacer con un fichero twitter4j.properties o con esta clase
//         ConfigurationBuilder cb = new ConfigurationBuilder();
//         cb.setDebugEnabled(true)
//         .setOAuthConsumerKey("yourConsumeKey")
//         .setOAuthConsumerSecret("yourConsumerSecret")
//         .setOAuthAccessToken("yourAccessToken")
//         .setOAuthAccessTokenSecret("yourTokenSecret");
//         TwitterFactory tf = new TwitterFactory(cb.build());
//         Twitter twitter = tf.getInstance();
//        Twitter twitter = new TwitterFactory().getInstance();
//        String busqueda = "\"Agustín Ruiz\"";
        
        twitter = new TwitterFactory().getInstance();
        
        try {
            Integer contador = 0;
            Query query = new Query(busqueda);
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    ++contador;

                    TweetPLN tweetPLN = new TweetPLN(tweet);
                    System.out.println("\n(" + contador + ") -----------------------------------------\n");
                    System.out.println(tweetPLN.getTweetId());
                    System.out.println("@" + tweetPLN.getUser());
                    System.out.println("\n" + tweetPLN.getTextoTweet());
                    System.out.println("\n" + tweetPLN.getTextoTweetProcesado());

                }
            } while (((query = result.nextQuery())) != null);
            System.out.println("\nNº DE RESULTADOS: " + contador);
        } catch (TwitterException e) {
            System.out.println("Error: " + e.toString());
        }

        /*
        
         Twitter twitter = TwitterFactory.getSingleton(); //Hay que podar la "broza" que saca
         Query query = new Query("#L6NBengoa");
         query.setCount(100);

         QueryResult result;
         try {
         result = twitter.search(query);
         System.out.println("\n\n\nTWEETS:");
         int contador = 0;
         for (Status status : result.getTweets()) {
         System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
         System.out.println("-----------------------------------------------------------");
         ++contador;
         }
         System.out.println("Nº de tweets: " + contador);

         while (result.hasNext()) {

         try {
         Thread.sleep(5000);
         } catch (InterruptedException ex) {
         java.util.logging.Logger.getLogger(TwitterSentimentAnalysis.class.getName()).log(Level.SEVERE, null, ex);
         }

         result = twitter.search(result.nextQuery());
         for (Status status : result.getTweets()) {
         System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
         System.out.println("-----------------------------------------------------------");
         ++contador;
         }
         System.out.println("Nº de tweets: " + contador);
         }
         } catch (TwitterException e) {
         System.out.println("Error: " + e.toString());
         }//*/
    }

}
