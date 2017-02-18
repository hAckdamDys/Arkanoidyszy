package Arkanoidyszy;

import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
//po prostu circle z predkascia i wielkoscia
public class Ball {
    //wielkosc roznic zaleznych od tego w ktora strone padlem jezdzimy podczas uderzenia
    private static final double ballPadleDiffer=0.7;

    private double batHeight;

    public void setPadleWidth(double batWidth) {
        this.batWidth = batWidth;
    }

    private double batWidth;

    public void setRadius(double radius) {
        this.circle.setRadius(radius);
    }

    public Circle getCircle() {
        return circle;
    }

    private Circle circle;
    private double dx,dy;//dy to szybkosc z jaka uzytkownik reagowac musi a dx prawo lewo
    private boolean circleLeft,circleUp;
    private int appWidth,appHeight;
    //podzial okregu na czesci ktore przy resecie pilki resetujemy i przy ruchu pilki ruszamy
    private static final int ileP = 10;//liczba czesci okregu ktore posiada kazda czesc
    private Point2D[] topSide;
    private Point2D[] lefSide;
    private Point2D[] rigSide;
    private Point2D[] botSide;
    private BrickGrid grid;
    private int waitinger;//ilsoc clocków po których znowu moze uderzyc klocek i sie odbic
    public Ball(double radius, double batWidth, double batHeight, int appWidth, int appHeight, BrickGrid grid){
        this.batWidth = batWidth;
        this.batHeight = batHeight;
        this.appHeight=appHeight;
        this.appWidth=appWidth;
        this.circle = new Circle(radius);
        circle.getStyleClass().add("ballStyle");
        topSide= new Point2D[ileP];
        lefSide= new Point2D[ileP];
        rigSide= new Point2D[ileP];
        botSide= new Point2D[ileP];
        //ogarnac grid
        this.grid=grid;
    }
    public void start(){
        double tmpX = appWidth/2;
        circle.setTranslateX(tmpX);
        double tmpY = appHeight-75;
        circle.setTranslateY(tmpY);
        double radi = this.circle.getRadius();
        double topThet = Math.PI*45/180;//poczatkowy kąt dla górnej części analogicznie dalej
        double lefThet = Math.PI*135/180;
        double botThet = Math.PI*225/180;
        double rigThet = Math.PI*315/180;
        double dP = Math.PI/(2*(ileP-1));//o tyle bedziemy zwiekszac kąt ileP - 1 bo chcemy od -45 stopni do 45 włącznie
        for(int i=0;i<ileP;i++){
            topSide[i]=new Point2D(tmpX+radi*Math.cos(topThet),tmpY+radi*Math.sin(topThet));
            lefSide[i]=new Point2D(tmpX+radi*Math.cos(lefThet),tmpY+radi*Math.sin(lefThet));
            botSide[i]=new Point2D(tmpX+radi*Math.cos(botThet),tmpY+radi*Math.sin(botThet));
            rigSide[i]=new Point2D(tmpX+radi*Math.cos(rigThet),tmpY+radi*Math.sin(rigThet));
            //zwiekszenie kątów
            topThet+=dP; lefThet+=dP; botThet+=dP; rigThet+=dP;
        }
        circleUp=true; circleLeft=true;
        dx=5*ballPadleDiffer; dy=5*ballPadleDiffer;
        waitinger=0;
    }
    public boolean move(double batx, UserAction action){
        //zapamietujemy sobie zmienne x,y pilki i jej r
        double circlex=circle.getTranslateX();
        double circley=circle.getTranslateY();
        double radius = circle.getRadius();

        //jesli przesuwamy odpowiednio do predkosci dx i dy odpowiednie x i y
        //dx i dy musza byc dodatnie!!!!!!
        double rdx =(circleLeft ? -dx : dx);
        double rdy =(circleUp ? -dy : dy);
        circle.setTranslateX(circlex + rdx);
        circle.setTranslateY(circley + rdy);
        for (int i = 0; i < ileP; i++) {
            topSide[i]=topSide[i].add(rdx,rdy);
            lefSide[i]=lefSide[i].add(rdx,rdy);
            botSide[i]=botSide[i].add(rdx,rdy);
            rigSide[i]=rigSide[i].add(rdx,rdy);
        }
        if(waitinger<=0) {
            int topCount = 0;
            int lefCount = 0;
            int botCount = 0;
            int rigCount = 0;
            boolean tookHit = false;
            for (int i = 0; i < ileP; i++) {
                int rdxi = (int)topSide[i].getX();
                int rdyi = (int)topSide[i].getY();
                if(rdxi > 0 && rdyi > 0 && rdxi < appWidth && rdyi < appHeight){
                    if (grid.checkBrick(rdxi,rdyi)) {
                        ++topCount;
                        if(!tookHit){
                            grid.takeHit(rdxi,rdyi);
                            tookHit=true;
                        }
                    }
                }
                rdxi = (int)lefSide[i].getX();
                rdyi = (int)lefSide[i].getY();
                if (rdxi > 0 && rdyi > 0 && rdxi < appWidth && rdyi < appHeight){
                    if (grid.checkBrick(rdxi,rdyi)) {
                        ++lefCount;
                        if(!tookHit){
                            grid.takeHit(rdxi,rdyi);
                            tookHit=true;
                        }
                    }
                }
                rdxi = (int)botSide[i].getX();
                rdyi = (int)botSide[i].getY();
                if (rdxi > 0 && rdyi > 0 && rdxi < appWidth && rdyi < appHeight) {
                    if (grid.checkBrick(rdxi,rdyi)) {
                        ++botCount;
                        if(!tookHit){
                            grid.takeHit(rdxi,rdyi);
                            tookHit=true;
                        }
                    }
                }
                rdxi = (int)rigSide[i].getX();
                rdyi = (int)rigSide[i].getY();
                if (rdxi > 0 && rdyi > 0 && rdxi < appWidth && rdyi < appHeight) {
                    if (grid.checkBrick(rdxi,rdyi)) {
                        ++rigCount;
                        if(!tookHit){
                            grid.takeHit(rdxi,rdyi);
                            tookHit=true;
                        }
                    }
                }
            }
            if (topCount != 0 || lefCount!=0 || rigCount!=0 || botCount!=0) {//jesli dotyka jakiegos bloczka
                double tmpDx;
                //4 przypadki zalezne od jak leci:
                //jesli leci w prawo:
                if (!circleLeft) {
                    if (circleUp) {//prawo góra
                        if (botCount > topCount) {
                            circleUp = false;
                            if (rigCount > lefCount) {
                                circleLeft = true;
                                tmpDx = dx;
                                dx = dy;
                                dy = tmpDx;
                            }
                        } else if (rigCount > lefCount) {
                            circleLeft = true;
                        }
                    }
                    else{//prawo dół
                        if (topCount > botCount) {
                            circleUp = true;
                            if (rigCount > lefCount) {
                                circleLeft = true;
                                tmpDx = dx;
                                dx = dy;
                                dy = tmpDx;
                            }
                        } else if (rigCount > lefCount) {
                            circleLeft = true;
                        }
                    }
                }//jesli leci w lewo
                else{
                    if (circleUp) {//lewo góra
                        if (botCount > topCount) {
                            circleUp = false;
                            if (rigCount < lefCount) {
                                circleLeft = false;
                                tmpDx = dx;
                                dx = dy;
                                dy = tmpDx;
                            }
                        } else if (rigCount < lefCount) {
                            circleLeft = false;
                        }
                    }
                    else{//lewo dół
                        if (topCount > botCount) {
                            circleUp = true;
                            if (rigCount < lefCount) {
                                circleLeft = false;
                                tmpDx = dx;
                                dx = dy;
                                dy = tmpDx;
                            }
                        } else if (rigCount < lefCount) {
                            circleLeft = false;
                        }
                    }
                }
                waitinger=0;
            }
        }
        else{//waitinger>0
            --waitinger;
        }
        //jesli pilka wyleciw lewo czyli x jest na lewo to zmieniamy circleLeft na false czyli teraz sie odbije w prawo
        if(circlex - radius <= 0){
            circleLeft=false;
        }//analogicznie w druga strone
        else if(circlex + radius >= appWidth){
            circleLeft=true;
        }
        //podobnie ze zmienna y tylko ze na dole jest teraz padle
        if(circley - radius <= 0){
            circleUp=false;
        }
        else if((circley + radius >= appHeight - batHeight) && (circlex + radius >= batx) && (circlex-radius<=batx+batWidth)){
            //jestesmy tutaj jesli pilka jest ponizej
            circleUp=true;
            if(action==UserAction.LEFT){
                if(circleLeft){
                    dx+=(ballPadleDiffer+ (Math.random() * 0.1*ballPadleDiffer));
                }
                else{
                    dx-=(ballPadleDiffer+ (Math.random() * 0.1*ballPadleDiffer));
                    if(dx<ballPadleDiffer){
                        dx=(ballPadleDiffer+ (Math.random() * 0.1*ballPadleDiffer));
                        circleLeft=true;
                    }
                }
            }
            else if (action==UserAction.RIGHT){
                if(circleLeft){
                    dx-=(ballPadleDiffer+ (Math.random() * 0.1*ballPadleDiffer));
                    if(dx<ballPadleDiffer){
                        dx=(ballPadleDiffer+ (Math.random() * 0.1*ballPadleDiffer));
                        circleLeft=false;
                    }
                }
                else{
                    dx+=(ballPadleDiffer+ (Math.random() * 0.1*ballPadleDiffer));
                }
            }
            dx=Math.min(dx,12*(ballPadleDiffer+ (Math.random() * 0.1*ballPadleDiffer)));//max speed
        }
        //40 to jest odleglosc padla od ziemi stad 40 musi byc nizej
        if(circley + radius >= appHeight-40){
            this.start();
            return true;
        }
        return false;
    }
}
