// src/components/BookingsForm.tsx
import React from 'react';
import { Formik, Form, Field, ErrorMessage, FormikHelpers } from 'formik';
import * as Yup from 'yup';
import DatePicker from 'react-datepicker';
import { sv } from 'date-fns/locale';
import 'react-datepicker/dist/react-datepicker.css';

interface BookingFormValues {
  date: Date | null;
  cleaningType: string;
  cleaner: string;
}

interface BookingsFormProps {
  cleaners: string[];
  onSubmit: (values: BookingFormValues) => void;
}

const cleaningTypes = ['Diamant', 'Silver', 'Flytt'];

const BookingsForm: React.FC<BookingsFormProps> = ({ cleaners, onSubmit }) => {
  const initialValues: BookingFormValues = {
    date: null,
    cleaningType: '',
    cleaner: '',
  };

  const validationSchema = Yup.object({
    date: Yup.date().required('Datum och tid är obligatoriskt'),
    cleaningType: Yup.string().required('Välj en typ av städning'),
    cleaner: Yup.string().required('Välj en städare'),
  });

  return (
    <Formik
      initialValues={initialValues}
      validationSchema={validationSchema}
      onSubmit={(values: BookingFormValues, { resetForm }: FormikHelpers<BookingFormValues>) => {
        onSubmit(values);
        resetForm();
      }}
    >
      {({ setFieldValue, values }) => (
        <Form className="booking-form">
          <h2>Boka Städning</h2>

          <div className="form-group">
            <label htmlFor="date">Datum och Tid</label>
            <DatePicker
              selected={values.date}
              onChange={(date: Date | null) => setFieldValue('date', date)}
              showTimeSelect
              dateFormat="Pp"
              timeCaption="Tid"
              locale={sv} 
              id="date"
              name="date"
              placeholderText="Välj datum och tid"
            />
            <ErrorMessage name="date">
              {(msg) => <div className="error" role="alert">{msg}</div>}
            </ErrorMessage>
          </div>

          <div className="form-group">
            <label>Typ av Städning</label>
            {cleaningTypes.map((type) => (
              <label key={type}>
                <Field type="radio" name="cleaningType" value={type} />
                {type}
              </label>
            ))}
            <ErrorMessage name="cleaningType">
              {(msg) => <div className="error" role="alert">{msg}</div>}
            </ErrorMessage>
          </div>

          <div className="form-group">
            <label htmlFor="cleaner">Välj Städare</label>
            <Field as="select" name="cleaner" id="cleaner">
              <option value="">-- Välj Städare --</option>
              {cleaners.map((cleaner) => (
                <option key={cleaner} value={cleaner}>
                  {cleaner}
                </option>
              ))}
            </Field>
            <ErrorMessage name="cleaner">
              {(msg) => <div className="error" role="alert">{msg}</div>}
            </ErrorMessage>
          </div>

          <button type="submit">Boka</button>
        </Form>
      )}
    </Formik>
  );
};

export default BookingsForm;
