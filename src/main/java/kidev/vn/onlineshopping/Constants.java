package kidev.vn.onlineshopping;

public interface Constants {
    interface RestApiReturnCode {
        int SUCCESS = 200;
        int BAD_REQUEST = 400;
        int SYS_ERROR = 500;
        int UNAUTHORIZED = 401;
        int NOT_FOUND = 404;
        String SUCCESS_TXT = "SUCCESS";
        String BAD_REQUEST_TXT = "BAD REQUEST";
        String SYS_ERROR_TXT = "SYSTEM ERROR";
        String UNAUTHORIZED_TXT = "UNAUTHORIZED";

        String NOT_FOUND_TXT = "NOT FOUND";
        String ACCESS_TOKEN_TXT = "INVALID ACCESS TOKEN";
        String NOT_LOGIN_USER = "Username hoặc Password không đúng";

    }

    interface AuthRoles {
        long ROLE_ADMIN_ID = 1L;
        long ROLE_STAFF_ID = 2L;
        long ROLE_USER_ID = 3L;
        String ROLE_ADMIN = "ADMIN";
        String ROLE_STAFF = "STAFF";
        String ROLE_USER = "USER";
    }

    interface StatusProduct {
        Integer NOTSELL = 0; //Chưa bán
        Integer SELLING = 1; // Đang bán
        Integer STOPSELL = 2;    // Dừng bán
        Integer STOPIMPORT = 3;  // Ngừng nhập
    }
}
