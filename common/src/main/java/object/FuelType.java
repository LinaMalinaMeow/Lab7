package object;

import java.io.Serializable;

/**
 * parameters for determining the type of fuel
 */
public enum FuelType implements Serializable {
    KEROSENE,
    DIESEL,
    ALCOHOL,
    MANPOWER,
    ANTIMATTER;
}
