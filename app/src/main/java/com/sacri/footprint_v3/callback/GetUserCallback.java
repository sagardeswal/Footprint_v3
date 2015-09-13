package com.sacri.footprint_v3.callback;

import com.sacri.footprint_v3.entity.UserDetails;

/**
 *
 */

public interface GetUserCallback {
    void done(UserDetails returnedUserDetails);
}
