import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

import java.awt.*;
import java.awt.event.*;

class CvCubePers extends Canvas
{   int centerX, centerY;
    Obj obj = new Obj();

    int iX(float x){return Math.round(centerX + x);}
    int iY(float y){return Math.round(centerY - y);}

    void line(Graphics g, int i, int j)
    {   Point2D p = obj.vScr[i], q = obj.vScr[j];
        g.drawLine(iX(p.x), iY(p.y), iX(q.x), iY(q.y));
    }

    public void paint(Graphics g) {
        Dimension dim = getSize();
        int maxX = dim.width - 1, maxY = dim.height - 1,
                minMaxXY = Math.min(maxX, maxY);
        centerX = maxX / 2;
        centerY = maxY / 2;
        obj.d = obj.rho * minMaxXY / obj.objSize;
        obj.eyeAndScreen(); // Arestas horizontais da base:
        line(g, 0, 1);
        line(g, 1, 2);
        line(g, 2, 3);
        line(g, 3, 0);
        // Arestas horizontais do topo:
        line(g, 4, 5);
        line(g, 5, 6);
        line(g, 6, 7);
        line(g, 7, 4);
        // Arestas verticais:
        line(g, 0, 4);
        line(g, 1, 5);
        line(g, 2, 6);
        line(g, 3, 7);
    }
}


class Obj // Contém dados do objeto 3D
{
    float rho, theta = 0.3F, phi = 1.3F, d, objSize,
            v11, v12, v13, v21, v22, v23, v32, v33, v43;    // Elementos da matriz de visualização V
    Point3D[] w;    // Coordenadas do mundo
    Point2D[] vScr; // Coordenadas de tela

    Obj() {
        w = new Point3D[8];
        vScr = new Point2D[8];
        // Superfície da base:
        w[0] = new Point3D(1, -1, -1);
        w[1] = new Point3D(1, 1, -1);
        w[2] = new Point3D(-1, 1, -1);
        w[3] = new Point3D(-1, -1, -1);
        // Superfície do topo:
        w[4] = new Point3D(1, -1, 1);
        w[5] = new Point3D(1, 1, 1);
        w[6] = new Point3D(-1, 1, 1);
        w[7] = new Point3D(-1, -1, 1);
        objSize = (float) Math.sqrt(12F);
        // = sqrt(2 * 2 + 2 * 2 + 2 * 2)
        // = distância entre dois vértices opostos
        rho = 5 * objSize; // Para razoável efeito de perspectiva
    }


    void initPersp() {
        float costh = (float) Math.cos(theta),
                sinth = (float) Math.sin(theta),
                cosph = (float) Math.cos(phi),
                sinph = (float) Math.sin(phi);
        v11 = -sinth;
        v12 = -cosph * costh;
        v13 = sinph * costh;
        v21 = costh;
        v22 = -cosph * sinth;
        v23 = sinph * sinth;
        v32 = sinph;
        v33 = cosph;
        v43 = -rho;
    }

    void eyeAndScreen() {
        initPersp();
        for (int i = 0; i < 8; i++) {
            Point3D p = w[i];
            float x = v11 * p.x + v21 * p.y,
                    y = v12 * p.x + v22 * p.y + v32 * p.z,
                    z = v13 * p.x + v23 * p.y + v33 * p.z + v43;
            vScr[i] = new Point2D(-d * x / z, -d * y / z);
        }
    }
}

