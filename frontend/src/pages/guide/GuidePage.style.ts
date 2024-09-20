import styled from "styled-components";
import media from "@/styles/media";

export const GuidPageLayout = styled.div`
  display: flex;
  flex-direction: column;
  gap: 6rem;
`;

export const GuideContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
`;

export const CardContainer = styled.div`
  cursor: pointer;

  display: flex;
  gap: 0.4rem;
  align-items: center;
  justify-content: center;

  width: 200px;
  height: 60px;
  margin: 0 auto;

  border: 2px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 12px;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular};

  &:hover {
    background-color: ${({ theme }) => theme.COLOR.primary1};

    ${media.medium`
      transform: scale(1.05);
    `}
    ${media.large`
      transform: scale(1.05);
    `}
  }

  span {
    font: ${({ theme }) => theme.TEXT.small_bold};
  }
`;

export const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  margin-bottom: 2rem;

  font: ${({ theme }) => theme.TEXT.small};
  line-height: 1.2rem;

  h1 {
    margin-top: 1rem;
    font: ${({ theme }) => theme.TEXT.large_bold};
  }

  h2 {
    margin-top: 1.4rem;
    font: ${({ theme }) => theme.TEXT.medium_bold};

    :first-child {
      margin-top: 0;
    }
  }

  h3,
  h4,
  p,
  span,
  li,
  em {
    font: ${({ theme }) => theme.TEXT.small};
  }

  p {
    font: ${({ theme }) => theme.TEXT.small};
    line-height: 2rem;

    span {
      font: ${({ theme }) => theme.TEXT.small_bold};
    }
  }

  img {
    width: 100%;
  }

  img.medium {
    width: 70%;
  }

  ul {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }

  li {
    margin-left: 2rem;
    list-style-type: initial;
  }

  li.second {
    margin-left: 4rem;
  }

  li.third {
    margin-left: 6rem;
  }

  em {
    font-style: italic;
  }

  a {
    font: ${({ theme }) => theme.TEXT.small_bold};
    color: ${({ theme }) => theme.COLOR.primary2};
  }

  a:hover {
    text-decoration: underline;
  }

  a:visited {
    color: ${({ theme }) => theme.COLOR.primary2};
  }
`;

export const StyledSquare = styled.div`
  padding: 1.6rem 1rem;
  font: ${({ theme }) => theme.TEXT.small_bold};
  background-color: ${({ theme }) => theme.COLOR.grey0};
  border-left: 4px solid ${({ theme }) => theme.COLOR.primary3};
`;

export const IconWrapper = styled.div`
  display: flex;
  gap: 0.4rem;
  align-items: center;

  a {
    font: ${({ theme }) => theme.TEXT.small_bold};
    color: ${({ theme }) => theme.COLOR.primary2};
  }

  a:hover {
    text-decoration: underline;
  }

  a:visited {
    color: ${({ theme }) => theme.COLOR.primary2};
  }
`;

export const StyledPre = styled.pre`
  overflow-x: auto;

  padding: 1rem;

  line-height: 1.2rem;

  background-color: ${({ theme }) => theme.COLOR.grey4};
  border-radius: 8px;
`;

export const StyledCode = styled.code`
  font: ${({ theme }) => theme.TEXT.xSmall};
  line-height: 2rem;
  color: ${({ theme }) => theme.COLOR.white};
`;

export const SubContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

export const StyledCodeLine = styled.span`
  padding: 0 0.4rem;

  font: ${({ theme }) => theme.TEXT.semiSmall} !important;
  color: ${({ theme }) => theme.COLOR.error};

  background-color: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 0.6rem;
`;
