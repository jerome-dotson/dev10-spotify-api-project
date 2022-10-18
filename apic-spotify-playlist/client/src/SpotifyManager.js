import axios from "axios";

axios.defaults.baseURL = 'https://api.spotify.com/v1';
axios.defaults.headers['Authorization'] = `Bearer ${window.localStorage.getItem("current_spotify_access_token")}`; //Will need to add a way for collaborators to use this
axios.defaults.headers['Content-Type'] = 'application/json';

/**
 * Get Current User's Profile
 * https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-current-users-profile
 * @returns {Promise}
 */
 export const getCurrentUserProfile = () => axios.get('/me');