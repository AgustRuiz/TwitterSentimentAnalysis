/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.pln.TwitterSentimentAnalysis.modelos;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import weka.associations.tertius.IndividualInstances;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 *
 * @author Agustín Ruiz Linares <www.agustruiz.es>
 */
public class TweetPLN {

    private Long tweetId;
    private String user;
    private String textoTweet;
    private Date fecha;
    private Integer favoritos; //Los favoritos son contabilizados en el momento de leer el tweet
    private Integer retweets; //Los retweets son contabilizados en el momento de leer el tweet
    //Atributos resultado del procesamiento
    private String textoTweetProcesado;
    private boolean tweetValido;
    private String tipoTweet; //Codificación del tipo según constantes enteras [0,255]

    //Tipos de tweets
    public static final String NO_PROCESADO = "no procesado";
    public static final String NO_VALIDO = "no válido";
    public static final String POSITIVO = "positivo";
    public static final String NEGATIVO = "negativo";
    public static final String NEUTRO = "neutro";

    //CONSTRUCTORES
    /**
     * Constructor por defecto
     */
    private TweetPLN() {
        this.tweetId = null;
        this.user = null;
        this.textoTweet = null;
        this.fecha = null;
        this.favoritos = null;
        this.retweets = null;
        this.tweetValido = false;
        this.tipoTweet = TweetPLN.NO_VALIDO;
    }

    /**
     * Constructor con status
     *
     * FALTA TRATAR EL TWEET
     */
    public TweetPLN(Status statusTweet) {
        this.tweetId = statusTweet.getId();
        this.user = statusTweet.getUser().getScreenName();
        this.textoTweet = statusTweet.getText();
        this.fecha = statusTweet.getCreatedAt();
        this.favoritos = statusTweet.getFavoriteCount();
        this.retweets = statusTweet.getRetweetCount();

        //FALTA TRATARLO -> SACARLO A UN MÉTODO PRIVADO
        this.procesarTweet();

        this.tweetValido = false;
        this.tipoTweet = TweetPLN.NO_PROCESADO;
    }

    /**
     * Procesar el Tweet
     *
     * Apoyado en el código de
     * http://zitnik.si/wordpress/2011/09/25/quick-intro-to-weka/
     */
    private void procesarTweet() {

        try {
            //Creación de los atributos
            Attribute atrTweet = new Attribute("tweet", (FastVector) null);
            atrTweet.isString();
//            System.out.println(atrTweet.isString());
            Attribute atrClase = new Attribute("clase", (FastVector) null);
            atrClase.isString();
//            System.out.println(atrClase.isString());
            FastVector atributos = new FastVector();
            atributos.addElement(atrTweet);
            atributos.addElement(atrClase);

            // 2 Crear el dataset
            Instances dataset = new Instances("tweetDataset", atributos, 0);

            //3 Añadir instancias (sólo 1)
            double[] instancia = new double[dataset.numAttributes()];
            instancia[0] = dataset.attribute("tweet").addStringValue(this.textoTweet);
            instancia[0] = dataset.attribute("clase").addStringValue(NO_PROCESADO);
            dataset.add(new Instance(1.0, instancia));

            //4 Preprocesado de StringToWordVector
            StringToWordVector filtro = new StringToWordVector();
            filtro.setInputFormat(dataset);
            dataset = Filter.useFilter(dataset, filtro);
            System.out.println(dataset);
            this.textoTweetProcesado = dataset.attribute(0).getRevision();

        } catch (Exception e) {
            System.out.println("Erró: " + e.toString());
            Logger.getLogger(TweetPLN.class.getName()).log(Level.SEVERE, null, e);
        }

        //Crear el dataset
//        Instances dataset = new Instances("TestInstances", atributos, 1);
//        dataset.setClassIndex(dataset.numAttributes() - 1);
        //Crear la instancia
//        Instance instancia = new Instance(dataset.numAttributes());
//        instancia.setDataset(dataset);
//        try {
//            instancia.setValue((Attribute) dataset.attribute(0), "cacafuti");
//        } catch (Exception e) {
//            System.out.println("ERRÓ: " + e.toString());
//        }
//        instancia.setClassValue(NO_PROCESADO);
        //Añadir la instancia al dataset
//        dataset.add(instancia);
    }

    //MÉTODOS
    public Long getTweetId() {
        return this.tweetId;
    }

    public String getUser() {
        return this.user;
    }

    public String getTextoTweet() {
        return this.textoTweet;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public Integer getFavoritos() {
        return this.favoritos;
    }

    public Integer getRetweets() {
        return this.retweets;
    }

    public String getTextoTweetProcesado() {
        return this.textoTweetProcesado;
    }

    public boolean isTweetValido() {
        return this.tweetValido;
    }

    public String getTipoTweet() {
        return this.tipoTweet;
    }

}
