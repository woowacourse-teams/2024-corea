import { InputHTMLAttributes, forwardRef } from "react";
import * as S from "@/components/common/input/Input.style";

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  error?: boolean;
  showCharCount?: boolean;
}

export const Input = forwardRef<HTMLInputElement, InputProps>(
  ({ error = false, showCharCount = false, value = "", onChange, ...rest }, ref) => {
    const onInputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
      if (onChange) {
        onChange(e);
      }
    };

    return (
      <S.InputWrapper>
        <S.StyledInput
          ref={ref}
          $error={error}
          value={value}
          onChange={onInputHandler}
          maxLength={rest.maxLength}
          {...rest}
        />
        {showCharCount && (
          <S.CharCount>
            {value.toString().length}
            {rest.maxLength ? ` / ${rest.maxLength}자` : ""}
          </S.CharCount>
        )}
      </S.InputWrapper>
    );
  },
);

Input.displayName = "Input";
