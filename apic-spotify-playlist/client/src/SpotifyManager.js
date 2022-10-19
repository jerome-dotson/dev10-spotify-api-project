import axios from "axios";


axios.defaults.baseURL = 'https://api.spotify.com/v1';
//axios.defaults.headers['Authorization'] = `Bearer ${window.localStorage.getItem("current_spotify_access_token")}`; //Will need to add a way for collaborators to use this
axios.defaults.headers['Content-Type'] = 'application/json';

/**
 * Get Current User's Profile
 * https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-current-users-profile
 * @returns {Promise}
 */


//Note that function calls with { data } objects created from the destructured response--function calls without that syntax 
//Will need to have objects created from them. 

//Can be destructured to get playlist ID
export const createPlaylist = async (token = window.localStorage.getItem("current_spotify_access_token"), user_id, name, isPublic = false, description ) => {
    const init = {
        headers: {
            Authorization: `Bearer ${token}`
        },
        data: {
            "name" : `${name}`,
            "description" : `${description}`,
            "public" : `${isPublic}`,
          }
    };

    const { data } = await axios.post(`users/${user_id}/playlists`, init
    );
    return data;
}

 export const getCurrentUserProfile = (token = window.localStorage.getItem("current_spotify_access_token")) => {
    const init = {
        headers: {
            Authorization: `Bearer ${token}`
        },
    };
   return axios.get('/me', init);}
 //returns response that can be destructured into object. Use
 //example syntax is const { data } = getCurrentUserProfile(); Should be called asynchronously with 
 //Reference URL for what method returns: https://developer.spotify.com/documentation/web-api/reference/#/operations/get-current-users-profile


export const getCurrentUserPlaylists = (token = window.localStorage.getItem("current_spotify_access_token"), playlist_id, limit = 20) => {
    const init = {
        headers: {
            Authorization: `Bearer ${token}`
        },
    };
    return axios.get(`/me/playlists?limit=${limit}`, init); //Gets a list of the current user's playlists. Returns a JSON with an array of playlist objects
    //Default limit is the maximum number of playlists returned. 
    //Reference URL for what method returns: https://developer.spotify.com/documentation/web-api/reference/#/operations/get-a-list-of-current-users-playlists
}


export const getPlaylist = async (token = window.localStorage.getItem("current_spotify_access_token"), playlist_id) => {
        const init = {
            headers: {
                Authorization: `Bearer ${token}`
            },
        };

        const { data } = await axios.get(`playlists/${playlist_id}`, init
        );
        return data;
}

export const addItemsToPlaylist = async (token = window.localStorage.getItem("current_spotify_access_token"), playlist_id, tracks) => {
    const init = {
        headers: {
            Authorization: `Bearer ${token}`
        },
        data: {
            "uris": `${tracks}`,
            "position": 0
          }
    };

    const { data } = await axios.post(`playlists/${playlist_id}/tracks`, init
    );
    return data;
}

export const deleteItemsFromPlaylist = async (token = window.localStorage.getItem("current_spotify_access_token"), playlist_id, tracks) => {
    const init = {
        headers: {
            Authorization: `Bearer ${token}`
        },
        data: {
            "uris": `${tracks}`,
          }
    };

    const { data } = await axios.delete(`playlists/${playlist_id}/tracks`, init
    );
    return data;
}


