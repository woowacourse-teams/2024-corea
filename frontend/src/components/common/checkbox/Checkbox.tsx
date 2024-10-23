import Icon from "../icon/Icon";
import React from "react";
import * as S from "@/components/common/checkbox/Checkbox.style";

interface CheckboxProps {
  id: string;
  label: string;
  checked: boolean;
  readonly?: boolean;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const Checkbox = ({ id, label, checked, readonly = false, onChange }: CheckboxProps) => {
  return (
    <S.CheckboxLabel htmlFor={id} readonly={readonly}>
      <S.CheckboxStyle checked={checked} readonly={readonly}>
        <S.HiddenCheckbox type="checkbox" id={id} name={id} checked={checked} onChange={onChange} />
        <S.CustomCheckbox>{checked && <Icon kind="check" size="1.6rem" />}</S.CustomCheckbox>
      </S.CheckboxStyle>
      <S.CheckboxText>{label}</S.CheckboxText>
    </S.CheckboxLabel>
  );
};

export default Checkbox;
