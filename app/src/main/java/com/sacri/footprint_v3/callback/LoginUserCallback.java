package com.sacri.footprint_v3.callback;

import com.sacri.footprint_v3.entity.UserDetails;

/**
 * Created by bazinga on 15/09/15.
 */
public interface LoginUserCallback {

    void done(UserDetails returnedUserDetails);
}
