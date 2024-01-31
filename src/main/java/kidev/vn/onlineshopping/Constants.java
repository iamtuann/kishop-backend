package kidev.vn.onlineshopping;

public interface Constants {
    public interface RestApiReturnCode {
        public int SUCCESS = 200;
        public int BAD_REQUEST = 400;
        public int SYS_ERROR = 500;
        public int UNAUTHORIZED = 401;
        public int NOT_FOUND = 404;
        public String SUCCESS_TXT = "SUCCESS";
        public String BAD_REQUEST_TXT = "BAD REQUEST";
        public String SYS_ERROR_TXT = "SYSTEM ERROR";
        public String UNAUTHORIZED_TXT = "UNAUTHORIZED";

        public String NOT_FOUND_TXT = "NOT FOUND";
        public String ACCESS_TOKEN_TXT = "INVALID ACCESS TOKEN";
        public String NOT_LOGIN_USER = "Username hoặc Password không đúng";

    }

    public interface AuthRoles {
        public long ROLE_ADMIN_ID = 1L;
        public long ROLE_STAFF_ID = 2L;
        public long ROLE_USER_ID = 3L;
        public String ROLE_ADMIN = "ADMIN";
        public String ROLE_STAFF = "STAFF";
        public String ROLE_USER = "USER";
    }

    public interface Gender {
        public long MALE_ID = 1L;
        public long FEMALE_ID = 2L;
        public long UNISEX_ID = 3L;
        public String MALE = "Nam";
        public String FEMALE = "Nữ";
        public String UNISEX = "Unisex";
    }

    public interface StatusProduct {
        public Integer NOTSELL = 0; //Chưa bán
        public Integer SELLING = 1; // Đang bán
        public Integer STOPSELL = 2;    // Dừng bán
        public Integer STOPIMPORT = 3;  // Ngừng nhập
    }
}
