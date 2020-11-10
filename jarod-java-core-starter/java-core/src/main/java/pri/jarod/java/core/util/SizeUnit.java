package pri.jarod.java.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 字节工具类
 *
 * {@link java.util.concurrent.TimeUnit}
 *
 * @author Jarod Kong
 */
public enum SizeUnit {
    /**
     *
     */
    B {
        @Override
        public BigDecimal toB(BigDecimal size)  {return div(size, div(C0, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toKb(BigDecimal size) {return div(size, div(C1, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toMb(BigDecimal size) {return div(size, div(C2, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toGb(BigDecimal size) {return div(size, div(C3, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toTb(BigDecimal size) {return div(size, div(C4, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toPb(BigDecimal size) {return div(size, div(C5, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toEb(BigDecimal size) {return div(size, div(C6, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toYb(BigDecimal size) {return div(size, div(C7, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toBb(BigDecimal size) {return div(size, div(C8, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toNb(BigDecimal size) {return div(size, div(C9, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toDb(BigDecimal size) {return div(size, div(C10,C0, DEFAULT_SCALE), DEFAULT_SCALE);}
    },

    /**
     *
     */
    KB {
        @Override
        public BigDecimal toB(BigDecimal size)  {return mul(size, div(C1, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toKb(BigDecimal size) {return div(size, div(C1, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toMb(BigDecimal size) {return div(size, div(C2, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toGb(BigDecimal size) {return div(size, div(C3, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toTb(BigDecimal size) {return div(size, div(C4, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toPb(BigDecimal size) {return div(size, div(C5, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toEb(BigDecimal size) {return div(size, div(C6, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toYb(BigDecimal size) {return div(size, div(C7, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toBb(BigDecimal size) {return div(size, div(C8, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toNb(BigDecimal size) {return div(size, div(C9, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toDb(BigDecimal size) {return div(size, div(C10,C1, DEFAULT_SCALE), DEFAULT_SCALE);}
    },

    /**
     *
     */
    MB {
        @Override
        public BigDecimal toB(BigDecimal size)  {return mul(size, div(C2, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toKb(BigDecimal size) {return mul(size, div(C2, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toMb(BigDecimal size) {return div(size, div(C2, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toGb(BigDecimal size) {return div(size, div(C3, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toTb(BigDecimal size) {return div(size, div(C4, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toPb(BigDecimal size) {return div(size, div(C5, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toEb(BigDecimal size) {return div(size, div(C6, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toYb(BigDecimal size) {return div(size, div(C7, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toBb(BigDecimal size) {return div(size, div(C8, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toNb(BigDecimal size) {return div(size, div(C9, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toDb(BigDecimal size) {return div(size, div(C10,C2, DEFAULT_SCALE), DEFAULT_SCALE);}
    },

    /**
     *
     */
    GB {
        @Override
        public BigDecimal toB(BigDecimal size)  {return mul(size, div(C3, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toKb(BigDecimal size) {return mul(size, div(C3, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toMb(BigDecimal size) {return mul(size, div(C3, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toGb(BigDecimal size) {return div(size, div(C3, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toTb(BigDecimal size) {return div(size, div(C4, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toPb(BigDecimal size) {return div(size, div(C5, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toEb(BigDecimal size) {return div(size, div(C6, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toYb(BigDecimal size) {return div(size, div(C7, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toBb(BigDecimal size) {return div(size, div(C8, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toNb(BigDecimal size) {return div(size, div(C9, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toDb(BigDecimal size) {return div(size, div(C10,C3, DEFAULT_SCALE), DEFAULT_SCALE);}
    },

    /**
     *
     */
    TB {
        @Override
        public BigDecimal toB(BigDecimal size)  {return mul(size, div(C4, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toKb(BigDecimal size) {return mul(size, div(C4, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toMb(BigDecimal size) {return mul(size, div(C4, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toGb(BigDecimal size) {return mul(size, div(C4, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toTb(BigDecimal size) {return div(size, div(C4, C4, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toPb(BigDecimal size) {return div(size, div(C5, C4, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toEb(BigDecimal size) {return div(size, div(C6, C4, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toYb(BigDecimal size) {return div(size, div(C7, C4, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toBb(BigDecimal size) {return div(size, div(C8, C4, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toNb(BigDecimal size) {return div(size, div(C9, C4, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toDb(BigDecimal size) {return div(size, div(C10,C4, DEFAULT_SCALE), DEFAULT_SCALE);}
    },

    /**
     *
     */
    PB {
        @Override
        public BigDecimal toB(BigDecimal size)  {return mul(size, div(C5, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toKb(BigDecimal size) {return mul(size, div(C5, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toMb(BigDecimal size) {return mul(size, div(C5, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toGb(BigDecimal size) {return mul(size, div(C5, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toTb(BigDecimal size) {return mul(size, div(C5, C4, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toPb(BigDecimal size) {return div(size, div(C5, C5, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toEb(BigDecimal size) {return div(size, div(C6, C5, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toYb(BigDecimal size) {return div(size, div(C7, C5, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toBb(BigDecimal size) {return div(size, div(C8, C5, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toNb(BigDecimal size) {return div(size, div(C9, C5, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toDb(BigDecimal size) {return div(size, div(C10,C5, DEFAULT_SCALE), DEFAULT_SCALE);}
    },
    EB {
        @Override
        public BigDecimal toB(BigDecimal size)  {return mul(size, div(C6, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toKb(BigDecimal size) {return mul(size, div(C6, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toMb(BigDecimal size) {return mul(size, div(C6, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toGb(BigDecimal size) {return mul(size, div(C6, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toTb(BigDecimal size) {return mul(size, div(C6, C4, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toPb(BigDecimal size) {return mul(size, div(C6, C5, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toEb(BigDecimal size) {return div(size, div(C6, C6, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toYb(BigDecimal size) {return div(size, div(C7, C6, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toBb(BigDecimal size) {return div(size, div(C8, C6, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toNb(BigDecimal size) {return div(size, div(C9, C6, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toDb(BigDecimal size) {return div(size, div(C10,C6, DEFAULT_SCALE), DEFAULT_SCALE);}
    },
    YB {
        @Override
        public BigDecimal toB(BigDecimal size)  {return mul(size, div(C7, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toKb(BigDecimal size) {return mul(size, div(C7, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toMb(BigDecimal size) {return mul(size, div(C7, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toGb(BigDecimal size) {return mul(size, div(C7, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toTb(BigDecimal size) {return mul(size, div(C7, C4, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toPb(BigDecimal size) {return mul(size, div(C7, C5, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toEb(BigDecimal size) {return mul(size, div(C7, C6, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toYb(BigDecimal size) {return div(size, div(C7, C7, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toBb(BigDecimal size) {return div(size, div(C8, C7, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toNb(BigDecimal size) {return div(size, div(C9, C7, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toDb(BigDecimal size) {return div(size, div(C10,C7, DEFAULT_SCALE), DEFAULT_SCALE);}
    },
    BB {
        @Override
        public BigDecimal toB(BigDecimal size)  {return mul(size, div(C8, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toKb(BigDecimal size) {return mul(size, div(C8, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toMb(BigDecimal size) {return mul(size, div(C8, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toGb(BigDecimal size) {return mul(size, div(C8, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toTb(BigDecimal size) {return mul(size, div(C8, C4, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toPb(BigDecimal size) {return mul(size, div(C8, C5, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toEb(BigDecimal size) {return mul(size, div(C8, C6, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toYb(BigDecimal size) {return mul(size, div(C8, C7, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toBb(BigDecimal size) {return div(size, div(C8, C8, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toNb(BigDecimal size) {return div(size, div(C9, C8, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toDb(BigDecimal size) {return div(size, div(C10,C8, DEFAULT_SCALE), DEFAULT_SCALE);}
    },
    NB {
        @Override
        public BigDecimal toB(BigDecimal size)  {return mul(size, div(C9, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toKb(BigDecimal size) {return mul(size, div(C9, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toMb(BigDecimal size) {return mul(size, div(C9, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toGb(BigDecimal size) {return mul(size, div(C9, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toTb(BigDecimal size) {return mul(size, div(C9, C4, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toPb(BigDecimal size) {return mul(size, div(C9, C5, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toEb(BigDecimal size) {return mul(size, div(C9, C6, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toYb(BigDecimal size) {return mul(size, div(C9, C7, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toBb(BigDecimal size) {return mul(size, div(C9, C8, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toNb(BigDecimal size) {return div(size, div(C9, C9, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toDb(BigDecimal size) {return div(size, div(C10,C9, DEFAULT_SCALE), DEFAULT_SCALE);}
    },
    DB {
        @Override
        public BigDecimal toB(BigDecimal size)  {return mul(size, div(C10, C0, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toKb(BigDecimal size) {return mul(size, div(C10, C1, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toMb(BigDecimal size) {return mul(size, div(C10, C2, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toGb(BigDecimal size) {return mul(size, div(C10, C3, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toTb(BigDecimal size) {return mul(size, div(C10, C4, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toPb(BigDecimal size) {return mul(size, div(C10, C5, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toEb(BigDecimal size) {return mul(size, div(C10, C6, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toYb(BigDecimal size) {return mul(size, div(C10, C7, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toBb(BigDecimal size) {return mul(size, div(C10, C8, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toNb(BigDecimal size) {return mul(size, div(C10, C9, DEFAULT_SCALE), DEFAULT_SCALE);}
        @Override
        public BigDecimal toDb(BigDecimal size) {return div(size, div(C10,C10, DEFAULT_SCALE), DEFAULT_SCALE);}
    };

    // Handy constants for conversion methods
    static final BigDecimal UNIT =  BigDecimal.valueOf(1024L);
    static final BigDecimal C0   =  BigDecimal.ONE;
    static final BigDecimal C1   =  C0.multiply(UNIT);
    static final BigDecimal C2   =  C1.multiply(UNIT);
    static final BigDecimal C3   =  C2.multiply(UNIT);
    static final BigDecimal C4   =  C3.multiply(UNIT);
    static final BigDecimal C5   =  C4.multiply(UNIT);
    static final BigDecimal C6   =  C5.multiply(UNIT);
    static final BigDecimal C7   =  C6.multiply(UNIT);
    static final BigDecimal C8   =  C7.multiply(UNIT);
    static final BigDecimal C9   =  C8.multiply(UNIT);
    static final BigDecimal C10  =  C9.multiply(UNIT);

    static BigDecimal mul(BigDecimal d, BigDecimal m, int scale) {
        return d.multiply(m).setScale(scale, RoundingMode.HALF_UP);
    }

    public BigDecimal toB(BigDecimal size) {
        throw new AbstractMethodError();
    }

    public BigDecimal toKb(BigDecimal size) {
        throw new AbstractMethodError();
    }

    public BigDecimal toMb(BigDecimal size) {
        throw new AbstractMethodError();
    }

    public BigDecimal toGb(BigDecimal size) {
        throw new AbstractMethodError();
    }

    public BigDecimal toTb(BigDecimal size) {
        throw new AbstractMethodError();
    }

    public BigDecimal toPb(BigDecimal size) {
        throw new AbstractMethodError();
    }

    public BigDecimal toEb(BigDecimal size) {
        throw new AbstractMethodError();
    }

    public BigDecimal toYb(BigDecimal size) {
        throw new AbstractMethodError();
    }

    public BigDecimal toBb(BigDecimal size) {
        throw new AbstractMethodError();
    }

    public BigDecimal toNb(BigDecimal size) {
        throw new AbstractMethodError();
    }

    public BigDecimal toDb(BigDecimal size) {
        throw new AbstractMethodError();
    }

    static final int DEFAULT_SCALE = 0;

    static BigDecimal div(BigDecimal c, BigDecimal c2, int scale) {
        return c.divide(c2).setScale(scale, RoundingMode.HALF_UP);
    }

}

