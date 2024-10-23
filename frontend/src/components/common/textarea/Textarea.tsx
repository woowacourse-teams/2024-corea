import { TextareaHTMLAttributes } from "react";
import * as S from "@/components/common/textarea/Textarea.style";

interface TextareaProps extends TextareaHTMLAttributes<HTMLTextAreaElement> {
  error?: boolean;
  showCharCount?: boolean;
}

export const Textarea = ({
  error = false,
  showCharCount = false,
  value = "",
  onChange,
  ...rest
}: TextareaProps) => {
  const onInputHandler = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (onChange) {
      onChange(e);
    }
  };

  return (
    <S.TextareaWrapper>
      <S.StyledTextarea
        $error={error}
        value={value}
        onChange={onInputHandler}
        maxLength={rest.maxLength}
        {...rest}
      />
      {showCharCount && (
        <S.CharCount aria-hidden>
          {value.toString().length}
          {rest.maxLength ? ` / ${rest.maxLength}자` : ""}
        </S.CharCount>
      )}
    </S.TextareaWrapper>
  );
};
