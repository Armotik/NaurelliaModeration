package fr.armotik.naurelliamoderation.utilsclasses;

public enum ResultCodeType {
    SUCCESS(true, 0),
    VPN(false, 1),
    TOO_MANY_ACCOUNTS(false, 2);

    private final boolean success;
    private final int code;

    ResultCodeType(boolean success, int code) {
        this.success = success;
        this.code = code;
    }

    /**
     * Get if the result is a success
     * @return true if the result is a success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Get the code of the result
     * @return code of the result
     */
    public int getCode() {
        return code;
    }

    /**
     * Get the ResultCodeType from the code
     * @param code code of the result
     * @return ResultCodeType from the code
     */
    public static ResultCodeType fromCode(int code) {
        for (ResultCodeType resultCodeType : values()) {
            if (resultCodeType.getCode() == code) {
                return resultCodeType;
            }
        }
        return null;
    }

    /**
     * Get the ResultCodeType from the success
     * @param success success of the result
     * @return ResultCodeType from the success
     */
    public static ResultCodeType fromSuccess(boolean success) {
        for (ResultCodeType resultCodeType : values()) {
            if (resultCodeType.isSuccess() == success) {
                return resultCodeType;
            }
        }
        return null;
    }
}
