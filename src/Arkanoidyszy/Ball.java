package Arkanoidyszy;

import javafx.geometry.Point2D;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball {
    //wielkosc roznic zaleznych od tego w ktora strone padlem jezdzimy podczas uderzenia
    private static final double ballPadleDiffer=0.7;
    private static final int ileP = 10;//liczba czesci okregu ktore posiada kazda czesc
    private Padle padle;
    private Circle circle;
    private double dx,dy;//dy to szybkosc z jaka uzytkownik reagowac musi a dx prawo lewo
    private boolean circleLeft,circleUp;
    private int appWidth,appHeight;
    //podzial okregu na 4 czesci ktore przy resecie pilki resetujemy i przy ruchu pilki ruszamy
    private Point2D[][] allSides;
    private BrickGrid grid;
    private int waitinger;//ilsoc clocków po których znowu moze uderzyc klocek i sie odbic
    private boolean isAlive;

    public boolean isAlive() {
        return isAlive;
    }

    public void setPadle(Padle padle) {
        this.padle = padle;
    }

    public void setRadius(double radius) {
        this.circle.setRadius(radius);
        double tmpX = circle.getTranslateX();
        double tmpY = circle.getTranslateY();
        double dP = Math.PI/(2*(ileP-1));//o tyle bedziemy zwiekszac kąt ileP - 1 bo chcemy od -45 stopni do 45 włącznie
        double thetas[] = new double[4];
        thetas[0] = Math.PI*45/180;//poczatkowy kąt dla górnej części analogicznie dalej
        thetas[1] = Math.PI*135/180;
        thetas[2] = Math.PI*225/180;
        thetas[3] = Math.PI*315/180;
        for(int i=0;i<ileP;i++){
            for(int j=0;j<4;j++){
                allSides[j][i]=new Point2D(tmpX+radius*Math.cos(thetas[j]),tmpY+radius*Math.sin(thetas[j]));
                thetas[j]+=dP;
            }
        }
    }

    public Circle getCircle() {
        return circle;
    }

    public Ball(double radius, Padle padle, int appWidth, int appHeight, BrickGrid grid){
        setPadle(padle);
        this.appHeight=appHeight;
        this.appWidth=appWidth;
        this.circle = new Circle(radius);
        circle.getStyleClass().add("ballStyle");
        allSides= new Point2D[4][ileP];
        for(int j=0;j<4;j++){
            allSides[j]=new Point2D[ileP];
        }
        this.grid=grid;
    }
    public void start(double tmpX, double tmpY){
        circle.setTranslateX(tmpX);
        circle.setTranslateY(tmpY);
        this.setRadius(this.circle.getRadius());
        circleUp=true; circleLeft=true;
        isAlive=true;
        dx=5*ballPadleDiffer; dy=5*ballPadleDiffer;
        waitinger=0;
    }
    public boolean move(UserAction action){
        //zapamietujemy sobie zmienne x,y pilki i jej r
        if(!isAlive){
            return true;
        }
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
            for(int j=0;j<4;j++) {
                allSides[j][i]=allSides[j][i].add(rdx,rdy);
            }
        }
        if(waitinger<=0) {
            int allCounts[] = new int[4];
            boolean tookHit = false;
            for (int i = 0; i < ileP; i++) {
                for (int j=0;j<4;j++) {
                    int rdxi = (int) allSides[j][i].getX();
                    int rdyi = (int) allSides[j][i].getY();
                    if (rdxi > 0 && rdyi > 0 && rdxi < appWidth && rdyi < appHeight) {
                        if (grid.checkBrick(rdxi, rdyi)) {
                            ++allCounts[j];
                            if (!tookHit) {
                                grid.takeHit(rdxi, rdyi);
                                tookHit = true;
                            }
                        }
                    }
                }
            }
            if (tookHit) {//jesli dotyka jakiegos bloczka
                //double tmpDx; <- use this if there is change on corner hits
                //4 przypadki zalezne od jak leci:
                //jesli leci w prawo:
                int topCount=allCounts[0];
                int lefCount=allCounts[1];
                int botCount=allCounts[2];
                int rigCount=allCounts[3];
                if (!circleLeft) {
                    if (circleUp) {//prawo góra
                        if (botCount > topCount) {
                            circleUp = false;
                            if (rigCount > lefCount) {
                                circleLeft = true;
//                                tmpDx = dx;
//                                dx = dy;
//                                dy = tmpDx;
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
//                                tmpDx = dx;
//                                dx = dy;
//                                dy = tmpDx;
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
//                                tmpDx = dx;
//                                dx = dy;
//                                dy = tmpDx;
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
//                                tmpDx = dx;
//                                dx = dy;
//                                dy = tmpDx;
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
        else if((circley + radius >= appHeight - padle.getHeight()) && (circlex + radius >= padle.getX()) && (circlex-radius<=padle.getX()+padle.getWidth())){
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
            isAlive=false;
            this.circle.setTranslateX(-1);
            this.circle.setTranslateY(-1);
            return true;
        }
        return false;
    }
}
