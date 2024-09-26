import * as S from "@/components/codeGuide/codeBlock/CodeBlock.style";

const CodeBlock = ({ children }: { children: string }) => (
  <S.StyledPre>
    <S.StyledCode>{children}</S.StyledCode>
  </S.StyledPre>
);

export default CodeBlock;
