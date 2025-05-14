import axios, { AxiosInstance, AxiosRequestConfig } from 'axios';
import { convertBlankToNull } from './common-funtion';
// import { DialogStore } from '@/store/dialogStore';
// import { useI18n } from 'vue-i18n';
// import { Offcanvas } from "bootstrap";
// import { useRouter } from 'vue-router';
// import { inject } from 'vue';

class ApiService {
    private apiClient: AxiosInstance; // Axios instance
    // private i18n;
    // private dialogStore;
    // private router;
    // private appConfig;

    constructor() {
        // this.i18n = useI18n();
        // this.dialogStore = DialogStore();
        // this.router = useRouter();
        // this.appConfig = inject('appConfig') as any;

        this.apiClient = axios.create({
            baseURL: 'https://localhost:7031/',
            timeout: 0,
            headers: {
            'Content-Type': 'application/json',
            },
        });

        /**
         * Request interceptor for logging and modifying requests
         * 
         * @param {Object} config - Axios request configuration
         * @returns {Object} Modified Axios request configuration
         */
        this.apiClient.interceptors.request.use((config) => {
            const userInfo = localStorage.getItem('userInfo');
            if (userInfo) {
                const { userToken } = JSON.parse(userInfo); // Assuming userInfo contains a token
                if (userToken) {
                    config.headers['Authorization'] = `Bearer ${userToken}`;
                }
            }
            return config;
        });

        /**
         * Response interceptor for logging and modifying responses
         * 
         * @param {Object} response - Axios response configuration
         * @returns {Object} Modified Axios response configuration
         */
        this.apiClient.interceptors.response.use(
            /**
             * Success interceptor
             */
            (response) => {
                return response;
            },
            /**
             * Error interceptor
             */
            (error) => {
                return Promise.reject(error);
            }
        );
    }

    /**
     * Make a GET request to the API
     * 
     * @param {string} url - API endpoint URL
     * @param {Object} [params={}] - Request parameters to be sent with the request
     * @returns {Promise} Promise that resolves with the response data or rejects with the error message
     */
    apiGet = async (url: string, params = {}, signal?: AbortSignal): Promise<any> => {
        try {
          const response = await this.apiClient.get(url, { ...params, signal });
          return response.data;
        } catch (error: any) {
          const err = this.getServerErrorMessage(error);
          throw err;
        }
    };
      
    /**
     * Make a POST request to the API
     * 
     * @param {string} url - API endpoint URL
     * @param {Object} [body={}] - Request body to be sent with the request
     * @returns {Promise} Promise that resolves with the response data or rejects with the error message
     */
    apiPost = async (url: string, params = {}): Promise<any> => {
        try {
          let dataNew = convertBlankToNull({ ...params });
          const response = await this.apiClient.post(url, dataNew);
          // const response = await this.apiClient.post(url, { ...params });
          return response.data;
        } catch (error: any) {
          const err = this.getServerErrorMessage(error);
          throw err;
        }
    };
      
    /**
     * Make a PUT request to the API
     * 
     * @param {string} url - API endpoint URL
     * @param {Object} [body={}] - Request body to be sent with the request
     * @returns {Promise} Promise that resolves with the response data or rejects with the error message
     */
    apiPut = async (url: string, params = {}): Promise<any> => {
        try {
          const response = await this.apiClient.put(url, convertBlankToNull({ ...params }));
          // const response = await this.apiClient.put(url, { ...params });
          return response.data;
        } catch (error: any) {
          const err = this.getServerErrorMessage(error);
          throw err;
        }
    };
      
    /**
     * Make a DELETE request to the API
     * 
     * @param {string} url - API endpoint URL
     * @returns {Promise} Promise that resolves with the response data or rejects with the error message
     */
    apiDelete = async (url: string, params = {}): Promise<any> => {
        try {
          const response = await this.apiClient.delete(url, { params: params });
          return response.data;
        } catch (error: any) {
          const err = this.getServerErrorMessage(error);
          throw err;
        }
    };

    getServerErrorMessage(error): string {
      const err = error?.response?.data?.Message || error?.response?.data || error?.message;
      const { userToken } = localStorage.getItem('userInfo') ? JSON.parse(localStorage.getItem('userInfo')??'') : '';
      if(err == 'TokenInvalid' || userToken == ''){
        // this.dialogStore.showErrorDialog(this.i18n.t('commonMsg.AP0003'), null, () => {
        //   const offcanvas = document.querySelectorAll(".offcanvas");
        //   offcanvas.forEach(item => {
        //     const bsOffcanvas = Offcanvas.getInstance(item) || new Offcanvas(item);
        //     bsOffcanvas.hide();
        //   });
        //   this.router.replace('/auth/login');
        // }, () => {}, true);
      }
      return err; 
    }

    uploadFile = async (url: string, fileList: any, params = {}): Promise<any> => { 
      const { userToken } = JSON.parse(localStorage.getItem('userInfo')??'');
      let json = JSON.stringify(params);
      let apiClient = axios.create({
        baseURL: 'https://localhost:7031/',
        timeout: 0,
        headers: {
          'Content-Type': 'application/octet-stream',
          'Authorization': `Bearer ${userToken}`
        },
      });

      const formData = new FormData();
      formData.append("requestJson", json); // set params to formData
      fileList?.forEach((file) => {
        formData.append('files', file.raw); // set files to formData
      });

      try {
        const response = await apiClient.post(url, formData);
        return response.data;
      } catch (error: any) {
        const err = error?.response?.data?.Message || error?.response?.data || error?.message;
        throw err;
      }
    }
}
export default ApiService;
  