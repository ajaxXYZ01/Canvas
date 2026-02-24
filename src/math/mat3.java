package math;

public class mat3 {
    
    public float [] elem;

    public mat3() {
        elem = new float [9]; // row-major
    }

    public mat3 setIdentity() {
        elem[0] = 1; elem[1] = 0; elem[2] = 0;
        elem[3] = 0; elem[4] = 1; elem[5] = 0;
        elem[6] = 0; elem[7] = 0; elem[8] = 1;
        return this;
    }

    // A + B
    public static void add(mat3 a, mat3 b, mat3 out) {
        float [] TMP_a = a.elem, TMP_b = b.elem, TMP_out = out.elem;

        TMP_out[0] = TMP_a[0] + TMP_b[0];
        TMP_out[1] = TMP_a[1] + TMP_b[1];
        TMP_out[2] = TMP_a[2] + TMP_b[2];

        TMP_out[3] = TMP_a[3] + TMP_b[3];
        TMP_out[4] = TMP_a[4] + TMP_b[4];
        TMP_out[5] = TMP_a[5] + TMP_b[5];

        TMP_out[6] = TMP_a[6] + TMP_b[6];
        TMP_out[7] = TMP_a[7] + TMP_b[7];
        TMP_out[8] = TMP_a[8] + TMP_b[8];
    }

    // A - B
    public static void sub(mat3 a, mat3 b, mat3 out) {
        float [] TMP_a = a.elem, TMP_b = b.elem, TMP_out = out.elem;

        TMP_out[0] = TMP_a[0] - TMP_b[0];
        TMP_out[1] = TMP_a[1] - TMP_b[1];
        TMP_out[2] = TMP_a[2] - TMP_b[2];

        TMP_out[3] = TMP_a[3] - TMP_b[3];
        TMP_out[4] = TMP_a[4] - TMP_b[4];
        TMP_out[5] = TMP_a[5] - TMP_b[5];

        TMP_out[6] = TMP_a[6] - TMP_b[6];
        TMP_out[7] = TMP_a[7] - TMP_b[7];
        TMP_out[8] = TMP_a[8] - TMP_b[8];
    }

    // AB
    public static void mul(mat3 a, mat3 b, mat3 out) {
        float [] ae = a.elem, be = b.elem, oe = out.elem;

        oe[0] = ae[0]*be[0] + ae[1]*be[3] + ae[2]*be[6];
        oe[1] = ae[0]*be[1] + ae[1]*be[4] + ae[2]*be[7];
        oe[2] = ae[0]*be[2] + ae[1]*be[5] + ae[2]*be[8];

        oe[3] = ae[3]*be[0] + ae[4]*be[3] + ae[5]*be[6];
        oe[4] = ae[3]*be[1] + ae[4]*be[4] + ae[5]*be[7];
        oe[5] = ae[3]*be[2] + ae[4]*be[5] + ae[5]*be[8];

        oe[6] = ae[6]*be[0] + ae[7]*be[3] + ae[8]*be[6];
        oe[7] = ae[6]*be[1] + ae[7]*be[4] + ae[8]*be[7];
        oe[8] = ae[6]*be[2] + ae[7]*be[5] + ae[8]*be[8];
    }

    public float det() {
        float [] m = elem;

        float a0 =   m[4]*m[8] - m[5]*m[7];
        float a1 = -(m[3]*m[8] - m[5]*m[6]);
        float a2 =   m[3]*m[7] - m[4]*m[6];

        return m[0]*a0 + m[1]*a1 + m[2]*a2;
    }

    public boolean invert(mat3 out) {
        float[] m = this.elem;
        float[] o = out.elem;

        float m0 = m[0], m1 = m[1], m2 = m[2];
        float m3 = m[3], m4 = m[4], m5 = m[5];
        float m6 = m[6], m7 = m[7], m8 = m[8];

        float c0 =  (m4*m8 - m5*m7);
        float c1 = -(m3*m8 - m5*m6);
        float c2 =  (m3*m7 - m4*m6);

        float det = m0*c0 + m1*c1 + m2*c2;

        if (Math.abs(det) < 1e-6f)
            return false;

        float invDet = 1.0f / det;

        o[0] =  c0 * invDet;
        o[1] = -(m1*m8 - m2*m7) * invDet;
        o[2] =  (m1*m5 - m2*m4) * invDet;

        o[3] =  c1 * invDet;
        o[4] =  (m0*m8 - m2*m6) * invDet;
        o[5] = -(m0*m5 - m2*m3) * invDet;

        o[6] =  c2 * invDet;
        o[7] = -(m0*m7 - m1*m6) * invDet;
        o[8] =  (m0*m4 - m1*m3) * invDet;

        return true;
    }

    public void print() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0)
                System.out.print("[\t");
            System.out.printf("%.2f\t", elem[i]);
            if(i % 3 == 2)
                System.out.println("]");
        }
    }
}