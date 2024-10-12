// src/components/AuthForm.tsx
import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage, FormikHelpers } from 'formik';
import * as Yup from 'yup';
import './AuthForm.css';

type AuthMode = 'login' | 'register';

interface FormValues {
  email: string;
  password: string;
  confirmPassword?: string;
}

const AuthForm: React.FC = () => {
  const [mode, setMode] = useState<AuthMode>('login');
  const [serverError, setServerError] = useState<string>('');

  const initialValues: FormValues = {
    email: '',
    password: '',
    confirmPassword: '',
  };

  const validationSchema = Yup.object({
    email: Yup.string()
      .email('Ogiltig e-postadress')
      .required('E-post är obligatoriskt'),
    password: Yup.string()
      .min(6, 'Lösenordet måste vara minst 6 tecken')
      .required('Lösenord är obligatoriskt'),
    confirmPassword: mode === 'register'
      ? Yup.string()
          .oneOf([Yup.ref('password')], 'Lösenorden matchar inte')
          .required('Bekräfta ditt lösenord')
      : Yup.string(),
  });

  const handleSubmit = async (
    values: FormValues,
    { setSubmitting, resetForm }: FormikHelpers<FormValues>
  ) => {
    setServerError('');
    try {
      if (mode === 'login') {
        // Simulera API-anrop för inloggning
        await fakeLogin(values.email, values.password);
        alert('Inloggning lyckades!');
      } else {
        // Simulera API-anrop för registrering
        await fakeRegister(values.email, values.password);
        alert('Registrering lyckades! Du kan nu logga in.');
        setMode('login');
        resetForm();
      }
    } catch (error: any) {
      setServerError(error.message || 'Ett fel uppstod. Försök igen.');
    } finally {
      setSubmitting(false);
    }
  };

  const toggleMode = () => {
    setMode(mode === 'login' ? 'register' : 'login');
    setServerError('');
  };

  // Simulerade API-anrop
  const fakeLogin = (email: string, password: string): Promise<void> => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (email === 'test@example.com' && password === 'password') {
          resolve();
        } else {
          reject(new Error('Felaktig e-post eller lösenord'));
        }
      }, 1000);
    });
  };

  const fakeRegister = (email: string, password: string): Promise<void> => {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (email !== 'test@example.com') {
          resolve();
        } else {
          reject(new Error('E-postadressen är redan registrerad'));
        }
      }, 1000);
    });
  };

  return (
    <div className="auth-container">
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
        {({ isSubmitting }) => (
          <Form
            className="auth-form"
            aria-label={mode === 'login' ? 'Inloggningsformulär' : 'Registreringsformulär'}
          >
            <h2>{mode === 'login' ? 'Logga In' : 'Registrera'}</h2>

            <div className="form-group">
              <label htmlFor="email">E-post</label>
              <Field
                type="email"
                id="email"
                name="email"
                aria-required="true"
                aria-describedby="emailError"
              />
              <ErrorMessage name="email">
                {(msg) => <div className="error" id="emailError" role="alert">{msg}</div>}
              </ErrorMessage>
            </div>

            <div className="form-group">
              <label htmlFor="password">Lösenord</label>
              <Field
                type="password"
                id="password"
                name="password"
                aria-required="true"
                aria-describedby="passwordError"
              />
              <ErrorMessage name="password">
                {(msg) => <div className="error" id="passwordError" role="alert">{msg}</div>}
              </ErrorMessage>
            </div>

            {mode === 'register' && (
              <div className="form-group">
                <label htmlFor="confirmPassword">Bekräfta Lösenord</label>
                <Field
                  type="password"
                  id="confirmPassword"
                  name="confirmPassword"
                  aria-required="true"
                  aria-describedby="confirmPasswordError"
                />
                <ErrorMessage name="confirmPassword">
                  {(msg) => <div className="error" id="confirmPasswordError" role="alert">{msg}</div>}
                </ErrorMessage>
              </div>
            )}

            {serverError && <div className="error" role="alert">{serverError}</div>}

            <div className="button-group">
              <button type="submit" disabled={isSubmitting}>
                {isSubmitting
                  ? mode === 'login'
                    ? 'Loggar In...'
                    : 'Registrerar...'
                  : mode === 'login'
                  ? 'Logga In'
                  : 'Registrera'}
              </button>
              <button type="button" className="switch-mode" onClick={toggleMode}>
                {mode === 'login' ? 'Registrera dig' : 'Logga In'}
              </button>
            </div>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default AuthForm;
