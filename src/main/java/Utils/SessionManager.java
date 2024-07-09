package Utils;

import Entites.UserEntity;

import java.util.prefs.Preferences;

public class SessionManager {

    private static final String USER_ID_KEY = "user_id";
    private static final String USER_ROLE_KEY = "user_role";
    private static Preferences preferences = Preferences.userRoot().node(SessionManager.class.getName());

    public static void saveSession(UserEntity user) {
        preferences.putInt(USER_ID_KEY, user.getId());
        preferences.put(USER_ROLE_KEY, user.getRole());
    }

    public static UserEntity getSession() {
        int userId = preferences.getInt(USER_ID_KEY, -1);
        String role = preferences.get(USER_ROLE_KEY, null);

        if (userId != -1 && role != null) {
            UserEntity user = new UserEntity();
            user.setId(userId);
            user.setRole(role);
            return user;
        }
        return null;
    }

    public static void clearSession() {
        preferences.remove(USER_ID_KEY);
        preferences.remove(USER_ROLE_KEY);
    }
}
