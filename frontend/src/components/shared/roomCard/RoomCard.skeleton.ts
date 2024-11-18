import styled from "styled-components";
import { SkeletonShimmer } from "@/styles/common";
import media from "@/styles/media";

export const Wrapper = styled.div`
  width: 100%;
  border-radius: 15px;

  ${media.small`
    height: 344px;
  `}

  ${media.medium`
    height: 364px;
  `}

  ${media.large`
    height: 384px;
  `}

  ${SkeletonShimmer}
`;
