// src/utils/cookieUtils.js
import { useCookies } from 'react-cookie';

// 쿠키를 가져오는 함수
export const useGetCookie = (cookieName) => {
    const [cookies] = useCookies([cookieName]);
    return cookies[cookieName] || null;
};

// 쿠키를 설정하는 함수
export const useSetCookie = (cookieName, value, options = {}) => {
    const [, setCookie] = useCookies([cookieName]);
    setCookie(cookieName, value, {
        path: '/', // 기본 경로 설정
        ...options,
    });
};

// 쿠키를 삭제하는 함수
export const useRemoveCookie = (cookieName, options = {}) => {
    const [, , removeCookie] = useCookies([cookieName]);
    removeCookie(cookieName, {
        path: '/', // 기본 경로 설정
        ...options,
    });
};
