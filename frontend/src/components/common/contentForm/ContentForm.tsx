import * as S from "@/components/common/contentForm/ContentForm.style";

interface ContentFormProps {
  title: string;
  children: React.ReactNode;
}

const ContentForm = ({ title, children }: ContentFormProps) => {
  return (
    <S.ContentFormContainer>
      <S.ContentFormTitle>{title}</S.ContentFormTitle>
      {children}
    </S.ContentFormContainer>
  );
};

export default ContentForm;
