import styled from "styled-components";
import { HeaderHeight } from "@/styles/layout";

export const ContentContainer = styled.div`
  width: 100%;
`;

export const ContentSection = styled.section`
  width: 100%;
  min-width: 375px;
  max-width: 1200px;
  min-height: calc(100vh - ${HeaderHeight});
  margin: auto;
  padding: 4rem 2rem;
`;
