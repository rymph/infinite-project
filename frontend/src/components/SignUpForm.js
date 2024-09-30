import React, { useState, useLayoutEffect } from 'react';
import { Form, Button, Container, Row, Col } from 'react-bootstrap';
import axios from 'axios';
import { useNavigate,Link } from 'react-router-dom';
import { getCookie } from '../common/cookieUtils';



function SignUpForm() {
    const [formData, setFormData] = useState({
        userId: '',
        password: '',
    });

    const [cookieData, setCookieData] = useState({
        email:'',
        roles:'',
        provider:'',
    })

    const [errors, setErrors] = useState({});
    const [message, setMessage] = useState('');
    const [termsAccepted, setTermsAccepted] = useState(false); // 약관 동의 상태

    const navigate = useNavigate();  // useNavigate 훅 사용
    const emailCookie = getCookie('INFINITE_INFO_PROVIDED');
    //백엔드에서 주는 쿠키 value의 형태는 INFINITE_INFO_PROVIDED=email=ws0501urm@naver.com&provider=naver&roles= 잘라서 사용할것

    useLayoutEffect(() => {
        if (emailCookie) {
            // 쿠키에서 email, provider, roles 추출
            const email = emailCookie.split('&')[0].split('=')[1];
            const provider = emailCookie.split('&')[1].split('=')[1];
            const roles = emailCookie.split('&')[2].split('=')[1];

            // 쿠키 데이터를 상태에 저장
            setCookieData({ email, roles, provider });

            // userId에 쿠키에서 추출한 이메일 설정 및 접근 제한
            setFormData((prevFormData) => ({
                ...prevFormData,
                userId: provider ? email : prevFormData.userId, // userId에 쿠키에서 추출한 이메일 설정, provider 조건은 없어도 될듯
                password: provider ? '********' : prevFormData.password, // provider가 있으면 password에 더미값 설정
            }));

            // 콘솔로 상태 확인
            console.log(cookieData);
        }
    }, [emailCookie]); // emailCookie만 의존성으로 설정



    const handleSubmit = async (e) => {
        e.preventDefault();
        const validationErrors = validate(formData);
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
        } else {
            try {
                const response = await axios.post('api', formData); //dummy api

                console.log('Sign up response:', response.data);

                // 성공 메시지 표시
                setMessage('signUp successful!');
                console.log(message);
                navigate('/login');


                // 이후 작업 수행 (예: 리다이렉션)
            } catch (error) {
                console.error('signUp error:', error);
                setMessage('signUp failed. Please check your credentials.');
                console.log(message);
            }
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };
    const handleTermsChange = () => {
        setTermsAccepted(!termsAccepted);
    };
    const validate = (values) => {
        const errors = {};
        if (!values.userId) errors.userId = 'userId is required';
        if (!values.password) errors.password = 'Password is required';
        return errors;
    };


    return (
        <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
            <Row>
                <Col>
                    <Form onSubmit={handleSubmit} className="p-4 border rounded">
                        <h2 className="text-center mb-4">Sign Up</h2>

                        <Form.Group controlId="userId" className="mt-3">
                            <Form.Label>User Id</Form.Label>
                            <Form.Control
                                type="euserId"
                                name="userId"
                                value={formData.userId}
                                onChange={handleChange}
                                isInvalid={!!errors.userId}
                                disabled={!!emailCookie} // 쿠키가 있을 때 입력 불가
                            />
                            <Form.Control.Feedback type="invalid">
                                {errors.userId}
                            </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group controlId="password" className="mt-3">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                type="password"
                                name="password"
                                value={formData.password}
                                onChange={handleChange}
                                isInvalid={!!errors.password}
                                disabled={!!emailCookie && cookieData.provider}
                            />
                            <Form.Control.Feedback type="invalid">
                                {errors.password}
                            </Form.Control.Feedback>
                        </Form.Group>

                        <div className="border p-3 mt-3" style={{ maxHeight: '100px', overflowY: 'auto'}}>
                            <Form.Label>
                                약관 및 동의사항
                            </Form.Label>
                            <div>
                                제1조(회원가입) 매일 5000원씩 이민효에게 입금됩니다<br/>
                                width, height 조정 필요하면 조정
                            </div>
                        </div>

                        <Form.Group controlId="terms" className="mt-1">
                            <Form.Check
                                type="checkbox"
                                label="동의합니다."
                                checked={termsAccepted}
                                onChange={handleTermsChange}
                                isInvalid={!!errors.terms}
                            />
                            <Form.Control.Feedback type="invalid">
                                {errors.terms}
                            </Form.Control.Feedback>
                        </Form.Group>

                        <Button type="submit" variant="primary" className="mt-4 w-100">
                            Sign Up
                        </Button>
                        <div className="text-center mt-3">
                            <Link to="/login">Already have an account? Login</Link>
                        </div>
                    </Form>
                </Col>
            </Row>
        </Container>
    );
}

export default SignUpForm;
