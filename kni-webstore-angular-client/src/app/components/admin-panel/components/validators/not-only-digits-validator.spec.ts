import { notOnlyDigitsValidator } from './not-only-digits-validator';
import { FormControl } from '@angular/forms';

describe('Not only digits Validator', () => {
    let control: FormControl;

    beforeEach(() => {
        control = new FormControl();
    });

    it('should return control value', (done: DoneFn) => {
        control.setValue('1234');
        const validatorResult = notOnlyDigitsValidator(control);
        expect(validatorResult).toBe('1234');
        done();
    });

    it('should return null', (done: DoneFn) => {
        control.setValue('abcd12');
        const validatorResult = notOnlyDigitsValidator(control);
        expect(validatorResult).toBe(null);
        done();
    });

    it('should return null', (done: DoneFn) => {
        control.setValue('asdas');
        const validatorResult = notOnlyDigitsValidator(control);
        expect(validatorResult).toBe(null);
        done();
    });

    it('should return null', (done: DoneFn) => {
        control.setValue('ASD');
        const validatorResult = notOnlyDigitsValidator(control);
        expect(validatorResult).toBe(null);
        done();
    });

    it('should return null', (done: DoneFn) => {
        control.setValue('ASD12');
        const validatorResult = notOnlyDigitsValidator(control);
        expect(validatorResult).toBe(null);
        done();
    });
});
