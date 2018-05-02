import { FormControl } from '@angular/forms';

export function notOnlyDigitsValidator(control: FormControl): any {
    const inputValue: string = control.value;
    if (inputValue === null || inputValue === '') {
        return null;
    }

    if (hasOnlyNumbers(inputValue)) {
        return {
            onlyDigits: {
                inputValue: inputValue
            }
        };
    }

    return null;
}

function hasOnlyNumbers(str: string): boolean {
    return !str.match('[a-z]+') && !str.match('[A-Z]+');
}

