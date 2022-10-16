import axios from "axios";

// Map for localStorage keys
const LOCALSTORAGE_KEYS = {
    spotifyAccessToken: 'spotify_access_token',
    spotifyRefreshToken: 'spotify_refresh_token',
    spotifyExpireTime: 'spotify_token_expire_time',
    spotifyTimestamp: 'spotify_token_timestamp',
};

// Map to retrieve localStorage values
const LOCALSTORAGE_VALUES = {
    spotifyAccessToken: window.localStorage.getItem(LOCALSTORAGE_KEYS.spotifyAccessToken),
    spotifyRefreshToken: window.localStorage.getItem(LOCALSTORAGE_KEYS.spotifyRefreshToken),
    spotifyExpireTime: window.localStorage.getItem(LOCALSTORAGE_KEYS.spotifyExpireTime),
    spotifyTimestamp: window.localStorage.getItem(LOCALSTORAGE_KEYS.spotifyTimestamp),
};


/**
 * Clear out all localStorage items we've set and reload the page
 * @returns {void}
 */
 export const logout = () => {
    // Clear all localStorage items
    for (const property in LOCALSTORAGE_KEYS) {
      window.localStorage.removeItem(LOCALSTORAGE_KEYS[property]);
    }
    // Navigate to homepage
    window.location = window.location.origin;
  };

/**
* Checks if the amount of time that has elapsed between the timestamp in localStorage
* and now is greater than the expiration time of 3600 seconds (1 hour).
* @returns {boolean} Whether or not the access token in localStorage has expired
*/
const hasTokenExpired = () => {
    const { spotifyAccessToken, spotifyTimestamp, spotifyExpireTime } = LOCALSTORAGE_VALUES;
    if (!spotifyAccessToken || !spotifyTimestamp) {
        return false;
    }
    const millisecondsElapsed = Date.now() - Number(spotifyTimestamp);
    return (millisecondsElapsed / 1000) > Number(spotifyExpireTime);
};

/**
 * Use the refresh token in localStorage to hit the /refresh_token endpoint
 * in our Node app, then update values in localStorage with data from response.
 * @returns {void}
 */
 const refreshToken = async () => {
    try {
      // Logout if there's no refresh token stored or we've managed to get into a reload infinite loop
      if (!LOCALSTORAGE_VALUES.spotifyRefreshToken ||
        LOCALSTORAGE_VALUES.spotifyRefreshToken === 'undefined' ||
        (Date.now() - Number(LOCALSTORAGE_VALUES.spotifyTimestamp) / 1000) < 1000
      ) {
        console.error('No refresh token available');
        logout();
      }
  
      // Use `/refresh_token` endpoint from our Node app
      const { data } = await axios.get(`/refresh_token?refresh_token=${LOCALSTORAGE_VALUES.spotifyRefreshToken}`);
  
      // Update localStorage values
      window.localStorage.setItem(LOCALSTORAGE_KEYS.spotifyAccessToken, data.access_token);
      window.localStorage.setItem(LOCALSTORAGE_KEYS.spotifyTimestamp, Date.now());
  
      // Reload the page for localStorage updates to be reflected
      window.location.reload();
  
    } catch (e) {
      console.error(e);
    }
  };

/**
 * Handles logic for retrieving the Spotify access token from localStorage
 * or URL query params
 * @returns {string} A Spotify access token
 */
const getAccessToken = () => {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const queryParams = {
        [LOCALSTORAGE_KEYS.spotifyAccessToken]: urlParams.get('access_token'),
        [LOCALSTORAGE_KEYS.spotifyRefreshToken]: urlParams.get('refresh_token'),
        [LOCALSTORAGE_KEYS.spotifyExpireTime]: urlParams.get('expires_in'),
    };
    const hasError = urlParams.get('error');

    // If there's an error OR the token in localStorage has expired, refresh the token
    if (hasError || hasTokenExpired() || LOCALSTORAGE_VALUES.spotifyAccessToken === 'undefined') {
        refreshToken();
    }

    // If there is a valid access token in localStorage, use that
    if (LOCALSTORAGE_VALUES.spotifyAccessToken && LOCALSTORAGE_VALUES.spotifyAccessToken !== 'undefined') {
        return LOCALSTORAGE_VALUES.spotifyAccessToken;
    }

    // If there is a token in the URL query params, user is logging in for the first time
    if (queryParams[LOCALSTORAGE_KEYS.spotifyAccessToken]) {
        // Store the query params in localStorage
        for (const property in queryParams) {
            window.localStorage.setItem(property, queryParams[property]);
        }
        // Set timestamp
        window.localStorage.setItem(LOCALSTORAGE_KEYS.spotifyTimestamp, Date.now());
        // Return access token from query params
        return queryParams[LOCALSTORAGE_KEYS.spotifyAccessToken];
    }

    // We should never get here!
    return false;
};

export const accessToken = getAccessToken();

/**
 * Axios global request headers
 * https://github.com/axios/axios#global-axios-defaults
 */
 axios.defaults.baseURL = 'https://api.spotify.com/v1';
 axios.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
 axios.defaults.headers['Content-Type'] = 'application/json';

 /**
 * Get Current User's Profile
 * https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-current-users-profile
 * @returns {Promise}
 */
export const getCurrentUserProfile = () => axios.get('/me');