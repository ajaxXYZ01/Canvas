package math;

public final class mat4 {
    
    public final float elem [];

    public mat4() {
        // automatically initialized to 0.0f for every element after creation
        elem = new float [16]; // row-major
    }
    
    public mat4 setIdentity() {
        elem[0]  = 1; elem[1]  = 0; elem[2]  = 0; elem[3]  = 0;
        elem[4]  = 0; elem[5]  = 1; elem[6]  = 0; elem[7]  = 0;
        elem[8]  = 0; elem[9]  = 0; elem[10] = 1; elem[11] = 0;
        elem[12] = 0; elem[13] = 0; elem[14] = 0; elem[15] = 1;
        return this;
    }

    // A + B
    public static void add(mat4 a, mat4 b, mat4 out) {
        float []  TMP_a = a.elem, TMP_b = b.elem, TMP_out = out.elem;

        TMP_out[0]  = TMP_a[0]  + TMP_b[0];
        TMP_out[1]  = TMP_a[1]  + TMP_b[1]; 
        TMP_out[2]  = TMP_a[2]  + TMP_b[2];
        TMP_out[3]  = TMP_a[3]  + TMP_b[3];

        TMP_out[4]  = TMP_a[4]  + TMP_b[4];
        TMP_out[5]  = TMP_a[5]  + TMP_b[5];
        TMP_out[6]  = TMP_a[6]  + TMP_b[6];
        TMP_out[7]  = TMP_a[7]  + TMP_b[7];

        TMP_out[8]  = TMP_a[8]  + TMP_b[8];
        TMP_out[9]  = TMP_a[9]  + TMP_b[9];
        TMP_out[10] = TMP_a[10] + TMP_b[10];
        TMP_out[11] = TMP_a[11] + TMP_b[11];

        TMP_out[12] = TMP_a[12] + TMP_b[12];
        TMP_out[13] = TMP_a[13] + TMP_b[13];
        TMP_out[14] = TMP_a[14] + TMP_b[14];
        TMP_out[15] = TMP_a[15] + TMP_b[15];
    }

    // A - B
    public static void sub(mat4 a, mat4 b, mat4 out) {
        float []  TMP_a = a.elem, TMP_b = b.elem, TMP_out = out.elem;

        TMP_out[0]  = TMP_a[0]  - TMP_b[0];
        TMP_out[1]  = TMP_a[1]  - TMP_b[1]; 
        TMP_out[2]  = TMP_a[2]  - TMP_b[2];
        TMP_out[3]  = TMP_a[3]  - TMP_b[3];

        TMP_out[4]  = TMP_a[4]  - TMP_b[4];
        TMP_out[5]  = TMP_a[5]  - TMP_b[5];
        TMP_out[6]  = TMP_a[6]  - TMP_b[6];
        TMP_out[7]  = TMP_a[7]  - TMP_b[7];

        TMP_out[8]  = TMP_a[8]  - TMP_b[8];
        TMP_out[9]  = TMP_a[9]  - TMP_b[9];
        TMP_out[10] = TMP_a[10] - TMP_b[10];
        TMP_out[11] = TMP_a[11] - TMP_b[11];

        TMP_out[12] = TMP_a[12] - TMP_b[12];
        TMP_out[13] = TMP_a[13] - TMP_b[13];
        TMP_out[14] = TMP_a[14] - TMP_b[14];
        TMP_out[15] = TMP_a[15] - TMP_b[15];
    }

    // A * B
    public static void mul(mat4 a, mat4 b, mat4 out) {

        float []  ae = a.elem, be = b.elem, oe = out.elem;

        oe[0]  = ae[0]*be[0]  + ae[1]*be[4]  + ae[2]*be[8]   + ae[3]*be[12];
        oe[1]  = ae[0]*be[1]  + ae[1]*be[5]  + ae[2]*be[9]   + ae[3]*be[13];
        oe[2]  = ae[0]*be[2]  + ae[1]*be[6]  + ae[2]*be[10]  + ae[3]*be[14];
        oe[3]  = ae[0]*be[3]  + ae[1]*be[7]  + ae[2]*be[11]  + ae[3]*be[15];

        oe[4]  = ae[4]*be[0]  + ae[5]*be[4]  + ae[6]*be[8]   + ae[7]*be[12];
        oe[5]  = ae[4]*be[1]  + ae[5]*be[5]  + ae[6]*be[9]   + ae[7]*be[13];
        oe[6]  = ae[4]*be[2]  + ae[5]*be[6]  + ae[6]*be[10]  + ae[7]*be[14];
        oe[7]  = ae[4]*be[3]  + ae[5]*be[7]  + ae[6]*be[11]  + ae[7]*be[15];

        oe[8]  = ae[8]*be[0]  + ae[9]*be[4]  + ae[10]*be[8]  + ae[11]*be[12];
        oe[9]  = ae[8]*be[1]  + ae[9]*be[5]  + ae[10]*be[9]  + ae[11]*be[13];
        oe[10] = ae[8]*be[2]  + ae[9]*be[6]  + ae[10]*be[10] + ae[11]*be[14];
        oe[11] = ae[8]*be[3]  + ae[9]*be[7]  + ae[10]*be[11] + ae[11]*be[15];

        oe[12] = ae[12]*be[0] + ae[13]*be[4] + ae[14]*be[8]  + ae[15]*be[12];
        oe[13] = ae[12]*be[1] + ae[13]*be[5] + ae[14]*be[9]  + ae[15]*be[13];
        oe[14] = ae[12]*be[2] + ae[13]*be[6] + ae[14]*be[10] + ae[15]*be[14];
        oe[15] = ae[12]*be[3] + ae[13]*be[7] + ae[14]*be[11] + ae[15]*be[15];
    }
    
    public float det() {
        float [] m = elem;

        float a0 = m[0]*m[5]  -  m[1]*m[4];
        float a1 = m[0]*m[6]  -  m[2]*m[4];
        float a2 = m[0]*m[7]  -  m[3]*m[4];
        float a3 = m[1]*m[6]  -  m[2]*m[5];
        float a4 = m[1]*m[7]  -  m[3]*m[5];
        float a5 = m[2]*m[7]  -  m[3]*m[6];

        float b0 = m[8]*m[13]  - m[9]*m[12];
        float b1 = m[8]*m[14]  - m[10]*m[12];
        float b2 = m[8]*m[15]  - m[11]*m[12];
        float b3 = m[9]*m[14]  - m[10]*m[13];
        float b4 = m[9]*m[15]  - m[11]*m[13];
        float b5 = m[10]*m[15] - m[11]*m[14];

        return a0*b5 - a1*b4 + a2*b3 + a3*b2 - a4*b1 + a5*b0;
    }

    public boolean invert(mat4 out) {
        float m [] = elem;
        float o [] = out.elem;

        float a0 = m[0]*m[5]  -  m[1]*m[4];
        float a1 = m[0]*m[6]  -  m[2]*m[4];
        float a2 = m[0]*m[7]  -  m[3]*m[4];
        float a3 = m[1]*m[6]  -  m[2]*m[5];
        float a4 = m[1]*m[7]  -  m[3]*m[5];
        float a5 = m[2]*m[7]  -  m[3]*m[6];

        float b0 = m[8]*m[13]  - m[9]*m[12];
        float b1 = m[8]*m[14]  - m[10]*m[12];
        float b2 = m[8]*m[15]  - m[11]*m[12];
        float b3 = m[9]*m[14]  - m[10]*m[13];
        float b4 = m[9]*m[15]  - m[11]*m[13];
        float b5 = m[10]*m[15] - m[11]*m[14];

        float det = a0*b5 - a1*b4 + a2*b3 + a3*b2 - a4*b1 + a5*b0;

        if (Math.abs(det) < 1e-6f)
                return false;
        
        float invDet = 1.0f / det;

        o[0] = (+ m[5]*b5  - m[6]*b4  + m[7]*b3)  * invDet;
        o[1] = (- m[1]*b5  + m[2]*b4  - m[3]*b3)  * invDet;
        o[2] = (+ m[13]*a5 - m[14]*a4 + m[15]*a3) * invDet;
        o[3] = (- m[9]*a5  + m[10]*a4 - m[11]*a3) * invDet;

        o[4] = (- m[4]*b5  + m[6]*b2  - m[7]*b1)  * invDet;
        o[5] = (+ m[0]*b5  - m[2]*b2  + m[3]*b1)  * invDet;
        o[6] = (- m[12]*a5 + m[14]*a2 - m[15]*a1) * invDet;
        o[7] = (+ m[8]*a5  - m[10]*a2 + m[11]*a1) * invDet;

        o[8]  = (+ m[4]*b4  - m[5]*b2  + m[7]*b0)  * invDet;
        o[9]  = (- m[0]*b4  + m[1]*b2  - m[3]*b0)  * invDet;
        o[10] = (+ m[12]*a4 - m[13]*a2 + m[15]*a0) * invDet;
        o[11] = (- m[8]*a4  + m[9]*a2  - m[11]*a0) * invDet;

        o[12] = (- m[4]*b3  + m[5]*b1  - m[6]*b0)  * invDet;
        o[13] = (+ m[0]*b3  - m[1]*b1  + m[2]*b0)  * invDet;
        o[14] = (- m[12]*a3 + m[13]*a1 - m[14]*a0) * invDet;
        o[15] = (+ m[8]*a3  - m[9]*a1  + m[10]*a0) * invDet;

        return true;
    }

    // --- Utils ---

    public void print() {
        for (int i = 0; i < 16; i++) {
            if (i % 4 == 0)
                System.out.print("[\t");
            System.out.printf("%.2f\t", elem[i]);
            if(i % 4 == 3)
                System.out.println("]");
        }
    }
}